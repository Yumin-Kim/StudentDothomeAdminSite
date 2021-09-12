#! /bin/bash
# $() 의미
exec 2> error.txt
# for i in $(ls)
# do
#         echo item : $i
# done


if [ -z $1 ]; then
        echo usage : $0 directory
        exit
fi
SRCD=$1
TGTD="./backup/"
OF=home-$(date +%Y%m%d).tar
tar -cvf $TGTD$OF $SRCD