#! /bin/bash
#if [ ! -d $1 ];then
#	 mkdir $1
#fi
#cd ~
#if [ -d $1 ];then
#	mv $1 $2	
#fi
sudo usermod -l $2 $1
sudo mv /home/$1 /home/$2
sudo usermod -m -d /home/$2 $2
sudo ln -Tfs /home/$2/Public /var/www/html/$1
sudo mv /var/www/html/$1 /var/www/html/$2
