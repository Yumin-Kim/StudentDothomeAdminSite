#! /bin/bash
# 주석
# $1 첫번째 인자 $2 두번째 인자

# 디렉토리 명 변경 로직
if [ -d $1 ];then
        mv $1 $2
fi

# 데이터베이스 명 변경 로직

#자동 실행
#! /bin/bash
service apache2 start
wait
service mysql start
wait
service ssh start

#매개변수를 통해 디렉토리 수정(도메인 주소 수정)
# docker 를 사용하여 진행하기 때문에 sudo 명령어가 먹히지 않아 사용하지 못함
usermod -l $2 $1
mv $1 $2
ln -Tfs /home/$2/Public /var/www/html/$1
mv /var/www/html/$1 /var/www/html/$2
