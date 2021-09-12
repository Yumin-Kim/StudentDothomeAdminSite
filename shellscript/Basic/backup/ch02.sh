#! /bin/bash
# 여러 파라미터 받아 보기
echo "arg1 = $0"
echo "arg1 = $1"
echo "arg1 = $2"
echo "arg1 = $3"
echo "arg1 = $*"

#결과
#oem@yumin:~/WebDir/ShellScript$ ./ch02.sh h e l l o
#arg1 = ./ch02.sh
#arg1 = h
#arg1 = e
#arg1 = l
#arg1 = h e l l o
