#! /bin/bash
#git pull로 최신화
# 원하는 디렉토리 및 파일 add
# 원하는 문구 입력할 수 있도록
# git push 할때 어떤 branh를 입력할것인지
read -p 'git add {directory} : ' directory 
read -p 'git commit -m {message} : ' message
read -p 'git push origin {??} : ' branch
read -n2 -p 'git pull origin : [Y,N] : ' pullValid
exec 2> error.txt
#global Variable
echo "입력한 값은 : $directory $message $branch $pullValid"

result=0

function gitPushFun(){
    git add $directory
    wait 
    git commit -m '"'"$message"'"'
    wait
    git push origin $branch
    result=2
}

if [[ $pullValid == "Y" || $pullValid == "y" ]]; then
    if [[ $(git pull origin master) =~ "Already" ]]; then
        echo "Already: Git pull"
        result=1
    else 
        gitPushFun
    fi
else
    gitPushFun
fi
if [[ $result -eq 1 ]]; then
    echo 'git pull이 이미 진행 되었습니다.'
fi

# # if [[ $? -eq 0 ]]; then
# echo $result
