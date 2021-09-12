#!/bin/bash
#: Usage : exam6.sh
echo "[$1 Directory list up]"
search=`/tmp/$(date +%Y-%m-%d).txt 2> /dev/null`
ls $1 > search
echo "==== $1 Search ===="
cat search
echo
ls $1 | wc -l > count.txt
cnt=`cat count.txt`
echo "Total $1 numbers : $cnt"