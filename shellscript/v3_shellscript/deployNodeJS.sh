#! /bin/bash
# sudo로 실행 필요

## command 실행후 즉시 $? 변수를 확인하게 되면 프로세스 성공 여부를 확인할 수 있다.

### 동일 포트 애플리케이션 동작 여부 파악
### 기존 포트를 사용하고 있는 애플리케이션은 동작하고 있지만 최근에 올린 애플리케이션이 동작하지 않게 된다.
### sudo 명령어를 통해서 진행했기 때문에 npx pm2 list를 root 권한으로 확인해야확인이 가능하다.

#### pm2로 동일한 포트를 
## 전역에 pm2 설치 필요
# local test Code
# GIT sudo ./deployNodeJS.sh oem GIT test app.js https://github.com/Yumin-Kim/testNodeJS.git testNodeJS
# SFTP,HTTP sudo ./deployNodeJS.sh oem SFTP app index.js sftpNodeJS

# 문자열 배열로 담기 
# parses=$(echo $result | tr " " "\n")
# arr=()
# for parse in $parses
# do
#         arr+=($parse)
# done
# echo "${arr[0]}"
# echo "${arr[1]}"_Hello
echo "1. 사용자 id : $1"
echo "2. 사용자_WAS 업로드 디렉토리 확인 : $2"
echo "3. 배포 방식 : $3"
echo "4. 등록할 애플리케이션 이름(pm2에 등록될 이름) : $4"
echo "5. EndPoint JavaScript File : $5" 
echo "6. 배포 방식에 따른 정보 => git 주소 ,SFTP 업로드한 디렉토리 , WAS 업로드 디렉토리 : $6"
echo "7. 사용자 데이터 베이스 이름 : $7"
echo "8. GIT 업로드 시 필수 리포티토리 이름 : $8"

stduentColumnId=$1
SSHUserName=$2
deployMethod=$3
applicationName=$4
endPointFile=$5
uploadInfo=$6
userDatabaseName=$7
repositoryName=$8

## development
## DB Connetion
rootDBuser=root
password=test1234
db=test01
## deploy Dir
# userpath=/home/$SSHUserName/Downloads
# workspace="test"
## Node Enveirment path
# NVM_DIR=/home/oem/.nvm

# #production
# # DB Connetion
# rootDBuser=root
# password=multi2021
# db=studentDothome
# # deploy Dir
userpath=/home/$SSHUserName/Public
workspace="wasWorkspace"
## Node Enveirment path
NVM_DIR=/root/.nvm

# SSH 연결 후 NodeJS 실행할 수 있는 함수
# 필요한 정보 endPoint , applicationName , directory Info
# 1. GIT SFTP WAS를 통해 업로드된 디렉토리 확인 
# 2. pm2 라이브러리 존재 여부 확인
# 2-1. pm2 존재하지 않으면 설치 ********
# 3 pm2 start {endPoint} --name {applicationName}
# 4 2초뒤 결과 확인 
function uploadAndRunNodeJS(){
    # NVM_DIR 주소는 배포할때 주의해서 진행 현재 nvm 설치는 디렉토리에 설치됨
    # root에 설치 해야지 편함
    [ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"  # This loads nvm
    [ -s "$NVM_DIR/bash_completion" ] && \. "$NVM_DIR/bash_completion"  # This loads nvm bash_completion
    nvmEnvirment=$?
    nvm use 14
    if [ -d "node_modules" ]; then
        echo 'Not install npm'    
        # npm i pm2
        npx pm2 start $endPointFile --name $applicationName
        wait
    else 
        echo 'install npm'    
        npm i
        wait
        npm i pm2
        wait
        echo $? 
        npx pm2 start $endPointFile --name $applicationName 
        wait
    fi
    
    # 결과는 즉시 확인할 수 없기때문에 1~5초 정도 delay후 정보 제공
    sleep 2s
    nodeJSResult=$(npx pm2 show $applicationName)
    # 성공 실패 여부 확인
    echo "============================================"
    if [[ "$nodeJSResult" == *errored* ]]; then
        echo 'failure deploy'
        updateErrorLogToDatabase
    else
        echo 'success deploy'
    fi
    echo "============================================"
    npx pm2 delete $applicationName
    # 로그 파일 삭제
    sudo rm -rf ~/.pm2/logs/$applicationName-error.log
    wait
    sudo rm -rf ~/.pm2/logs/$applicationName-out.log
    wait
}

# 업로드 폴더가 존재 여부 파악 존재 X => 1
function ValidationDirectory(){
    if [ ! -d $uploadInfo ]; then
        echo 1
    else
        echo 0
    fi
}

function filterDeployMethod(){
    method=$1
    directoryValidationResult=$(ValidationDirectory)
    if [ "SFTP" == $method ]; then
        sudo chmod -R 777 $uploadInfo/*
        cd $uploadInfo
        echo 'SFTP connection'
    fi
    if [ "HTTP" == $method ]; then
        sudo chmod -R 777 $uploadInfo/*
        cd $uploadInfo
        echo 'HTTP connection'
    fi
    if [ "GIT" == $method ]; then
        gitCommand=$(git clone $uploadInfo)
        processResult=$?
        if [ $processResult -eq 0 ]; then 
            echo 'Success git clone command'
            cd $repositoryName
        fi
        if [ $processResult -gt 0 ]; then
            echo 'fail git clone command'
            cd $repositoryName
        fi
    fi
    if [ $directoryValidationResult -eq 0 ]; then
        uploadAndRunNodeJS  
    else
        echo "Failure not found deployDirectory"
    fi    

}

function updateErrorLogToDatabase(){
        logFile=$(sudo cat /root/.pm2/logs/$applicationName-error.log)
        # integratedErrorLogId=$(mysql -u$user -p$password $db -e "SELECT l.local_was_info_id FROM integrated_error_log as l left join local_was_info as w on w.local_was_info_id = l.local_was_info_id where w.student_id=$stduentColumnId and w.created='$craetedDate';")
        integratedErrorLogId=$(mysql -u$rootDBuser -p$password $db -e "select l.integrated_error_log_id from integrated_error_log as l left join local_was_info as wasinfo on wasinfo.local_was_info_id = l.local_was_info_id where wasinfo.student_id = $stduentColumnId and wasinfo.created = (select MAX(created) from local_was_info);")
        wait
        echo $integratedErrorLogId
        parses=$(echo $integratedErrorLogId | tr " " "\n")
        arr=()
        for parse in $parses
        do
                arr+=($parse)
        done
        parse=${logFile//,// }
        parse1=${parse//"'"/ }
        # root 일시
        # select후 배열 추가 코드
        # result << select student_code , password from student
        # mysql -u$rootDBuser -p$password $db -e "update integrated_error_log set error_logs='$logFile' , last_modified = NOW() where ( integrated_error_log_id =$integratedErrorLogId );"
        # mysql -u$rootDBuser -p$password $db -e "update integrated_error_log set error_logs='$logFile' , last_modified = NOW() where (integrated_error_log_id=$integratedErrorLogId);"
        mysql -u$rootDBuser -p$password $db -e "update integrated_error_log set integrated_error_log.error_logs='$parse1' , integrated_error_log.last_modified=NOW() where (integrated_error_log.integrated_error_log_id = ${arr[1]});"
        rootLogUpdateResultStatusCode=$?
        # 결과에 따른 부가적인 
        
        # 사용자 일시(테이블 명은 waserrorlogs id(Integer_AutoIncrement),was_info_id(foreginKey),createdAt(DateTime),last_modified(DateTime),delete(DateTime),is_delete(boolean),error_logs(Text))
        # Spring-boot  
        # 테이블 생성 및 insert 쿼리 진행(wasid , 생성 날짜 입력)
        # update 쿼리 필요 사용자와 통합
        # deployWasInfoId=$(mysql -u$user -p$password $db -e "select local_was_info_id from local_was_info left join  where student_id=$stduentColumnId and created='$createdDate';")
        deployWasInfoId=$(mysql -u$rootDBuser -p$password $db -e "select local_was_info.local_was_info_id from local_was_info where student_id=$stduentColumnId and created= (select MAX(created) from local_was_info);")
        echo $deployWasInfoId

        parses=$(echo $deployWasInfoId | tr " " "\n")
        arr=()
        for parse in $parses
        do
                arr+=($parse)
        done
        wait
        echo  ${arr[1]}
        userDeployErrorLogId=$(mysql -u$rootDBuser -p$password $userDatabaseName -e "select id from was_error_logs where was_error_logs.was_info_id = ${arr[1]};")
        echo $userDeployErrorLogId
        parses=$(echo $userDeployErrorLogId | tr " " "\n")
        arr=()
        for parse in $parses
        do
                arr+=($parse)
        done
        wait
        mysql -u$rootDBuser -p$password $userDatabaseName -e "update was_error_logs set was_error_logs.error_logs = '$parse1' , last_modified=NOW() WHERE (id =${arr[1]});"
        wait
        userLogUpdateResultStatusCode=$?
    
}

cd $userpath
if [ -d $workspace ]; then
    cd $userpath/$workspace
    filterDeployMethod $deployMethod
else 
    sudo mkdir $workspace
    chmod -R 777 $workspace
    cd $userpath/$workspace
    filterDeployMethod $deployMethod
fi


