#! /bin/bash


#현재시각을 담아둘 변수
nowdate=$(date +"%Y-%m-%d %H:%M:%S")

# 텍스트와 crontab이 수행될 시각을 같이 표현해서 check_test라는 파일에 작성한다.
echo "It is ${nowdate}, crontab test success." >> chech_test.txt

