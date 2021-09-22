#! /bin/bash
path="/home/$1"
sudo adduser --disabled-password --gecos "" $1
wait
sudo chpasswd <<< $1:$2
sudo chown root:$1 /home/$1
sudo chmod 755 /home/$1
sudo mkdir /home/$1/Public
sudo chown root:$1 /home/$1/Public
sudo chmod 775 /home/$1/Public
sudo usermod -G filetransfer $1
sudo ln -s /home/$1/Public  /var/www/html/$1
wait
if [ -d $path ];then
	echo "True"
	exit 0
fi
	echo "False"
