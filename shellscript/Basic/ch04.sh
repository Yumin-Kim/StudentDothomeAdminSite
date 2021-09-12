#!/bin/bash

read -n 10 -p "input Data :" var1
#var2="HELO"
if [ $var1 == "Hello" ]; then
	echo "if $var1 world"
elif [ $var1 == "world" ]; then
	echo "elif Hello $var1"
else 
	echo "else $var1"
fi


VAL1=3
VAL2=3
if [ ${VAL1} -eq ${VAL2} ] ; then
	echo "equal"
fi
STR1=""
STR2="A"
STR3="A"
# String이 존재하지 않으면 실행
if [[ -z $STR1 ]]; then
	echo "[[ -z STR1 ]] $STR1"
fi
# String이 존재하면 실행
if [ -n $STR2 ]; then
	echo "[[ -n STR1 ]] $STR2"
fi
# String이 동일하면 실행
if [ $STR2 == $STR3 ]; then
	echo "[[ $STR2 == $STR3 ]]"
fi