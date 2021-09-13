#! /bin/bash
#git pull로 최신화
# 원하는 디렉토리 및 파일 add
#
# 원하는 문구 입력할 수 있도록
# git push 할때 어떤 branh를 입력할것인지
read -p 'git add {directory} : ' directory 
read -p 'git commit -m {message} : ' message
read -p 'git push origin {??} : ' branch

echo "입력한 값은 : $directory $message $branch"
# if [[ $(git pull origin master) =~ "Already" ]]; then
#     echo "Already: Git pull"
#     exit 1
# fi
git add $directory
wait 
git commit -m '"'"$message"'"'
wait
git push origin $branch



