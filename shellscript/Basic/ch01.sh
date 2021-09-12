#! /bin/bash
#: 현재 쉘 스크립트를 배우고 있습니다.
animal=tiger
color=green
# > 출력 결과를 기록하며 append가 아니다.
# >> 출력 결과를 기록하며 append가 된다.
#"" 더불 쿼테이션 은 변수를 확인할 수 있다. 
echo "$animal's color is $color"
#'' 싱글 쿼테이션 문자 그대로의 의미를 갖도록 텍스트 보호
echo '$animals color is $color'
if [ -f 'ch01.sh' ];
then
echo "Hello"
fi

echo "============"
date +%Y-%m-%d
echo "============"
