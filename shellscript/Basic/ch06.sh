#! /bin/bash
# exec 실행에 있어 모든것을 처리할 수 있도록해준다.
# 0 표준 입력
# 1 표준 출력
# 2 표준 에러
# 뛰어쓰기 주의 할것
# exec 1>>
exec 2>> error.txt
# exec 1>> out.txt  하게 된다면 터미널에 나오는 문구들이 out.txt에 write되게 된다.
echo 'for val in {1..5}'
for val in {1..5}
do 
    echo "${val}"
done
echo 'for val in $(seq 5 10)'
for val in $(seq 5 10);
do 
    echo "$val"
done
count=0
for file in $@
do 
    if [ -f $file ]; then
        wc -l $file
    elif [ -d $file ]; then
        for subfile in $file/*
            do
                echo "sub file :" 
                wc -l $subfile 
            done
    fi
# 프로그래밍 언어처럼 숫자를 더하고 싶은경우 let을 통해 선언 필요 JavaScript ES5문법
let count=count+1
done
echo "Total count : $count"
