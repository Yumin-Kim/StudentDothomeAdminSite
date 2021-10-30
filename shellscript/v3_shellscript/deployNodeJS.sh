#! /bin/bash
# sudo로 실행 필요

## command 실행후 즉시 $? 변수를 확인하게 되면 프로세스 성공 여부를 확인할 수 있다.

### 동일 포트 애플리케이션 동작 여부 파악
### 기존 포트를 사용하고 있는 애플리케이션은 동작하고 있지만 최근에 올린 애플리케이션이 동작하지 않게 된다.
### sudo 명령어를 통해서 진행했기 때문에 npx pm2 list를 root 권한으로 확인해야확인이 가능하다.

#### pm2로 동일한 포트를 

# local test Code
# GIT sudo ./deployNodeJS.sh oem GIT test app.js https://github.com/Yumin-Kim/testNodeJS.git testNodeJS
# SFTP,HTTP sudo ./deployNodeJS.sh oem SFTP app index.js sftpNodeJS

echo "1. 사용자_WAS 업로드 디렉토리 확인 : $1"
echo "2. 배포 방식 : $2"
echo "3. 등록할 애플리케이션 이름(pm2에 등록될 이름) : $3"
echo "4. EndPoint JavaScript File : $4" 
echo "5. 배포 방식에 따른 정보 => git 주소 ,SFTP 업로드한 디렉토리 , WAS 업로드 디렉토리 : $5"
echo "6. GIT 업로드 시 필수 리포티토리 이름 : $6"

SSHUserName=$1
deployMethod=$2
applicationName=$3
endPointFile=$4
uploadInfo=$5
repositoryName=$6

## development
## DB Connetion
rootDBuser=root
password=test1234
db=test01
## deploy Dir
userpath=/home/$SSHUserName/Downloads
workspace="test"
## Node Enveirment path
NVM_DIR=/home/oem/.nvm

# #production
# # DB Connetion
# rootDBuser=root
# password=multi2021
# db=studentDothome
# # deploy Dir
# userpath=/home/$1/Public
# workspace="wasWorkspace"
## Node Enveirment path
# NVM_DIR=/home/root/.nvm

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
    nvm use 14
    if [ -e "node_modules" ]; then
        sudo npm i pm2
        npx pm2 start $endPointFile --name $applicationName
        wait
    else 
        sudo npm i
        wait
        sudo npm i pm2
        wait
        echo $? 
        # pm2로 변경 필요
        runNodeJs=$(npx pm2 start $endPointFile --name $applicationName) 
        wait
    fi
    # 결과는 즉시 확인할 수 없기때문에 1~5초 정도 delay후 정보 제공
    sleep 2s
    nodeJSResult=$(npx pm2 show $applicationName)
    # 성공 실패 여부 확인
    echo "============================================"
    if [[ "$nodeJSResult" == *errored* ]]; then
        echo 'failure deploy'
        # root 일시
        # select후 배열 추가 코드
        # result << select student_code , password from student
        # result=$(mysql -u$user -p$password $db -e "select user from user;")
        # parses=$(echo $result | tr " " "\n")
        # arr=()
        # for parse in $parses
        # do
        #         arr+=($parse)
        # done
        # echo "${arr[0]}"
        # echo "${arr[1]}"_Hello
        # 사용자 일시
        
        # Spring-boot에서 insert 쿼리 동작 후  
        # update 쿼리 필요 사용자와 통합
        echo $(mysql -u$user -p$password $db -e "select * from user;")
        # mysql -u username -puserpass dbname -e "UPDATE mytable SET mycolumn = 'myvalue' WHERE id='myid'";
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

function filterDeployMethod(){
    method=$1
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
    uploadAndRunNodeJS  
}



cd $userpath
# if [ -e "wasWorkspace" ]; then
if [ -e $workspace ]; then
    cd $userpath/$workspace
    filterDeployMethod $deployMethod
else 
    sudo mkdir $workspace
    chmod -R 777 $workspace
    cd $userpath/$workspace
    filterDeployMethod $deployMethod
fi

