#! /bin/bash

count=$#
i=1
while [ $i -le $count ]
do
    sudo deluser -remove-all-files $1
    wait
    sudo rm -rf /home/$1
    sudo rm -rf /var/www/html/$1
    wait
    i=`expr $i + 1`
    shift 
done


