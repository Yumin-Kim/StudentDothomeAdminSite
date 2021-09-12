#! /bin/bash
#: 현재 쉘 스크립트를 배우고 있습니다.
read -p "input file name : " file
echo "input file name $file"
# 파일 권한 확인 
if [ -r $file ]; then
    echo "File has read access"
else 
    echo "File does not have read access"
fi
# 파일 권한 확인 
if [ -w $file ]; then
    echo "File has write permission"
else
    echo "File does not have write permission"
fi
# 파일 권한 확인 
if [ -x $file ]; then
    echo "File has excute permission"
else 
    echo "File does not have excute permission"
fi
# 존재 여부 확인
if [ -e $file ]; then
    echo "File exist"
else 
    echo "File does not exist"
fi
#디렉토리 인지 확인
if [ -d $file ]; then
    echo "File is a directory"
else 
    echo "File is not a directory"
fi
