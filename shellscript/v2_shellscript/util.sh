#! /bin/bash
# ssh modify ssh Directory & name 
sudo usermod -l $2 $1
sudo mv /home/$1 /home/$2
sudo usermod -m -d /home/$2 $2
# ssh change group 
sudo chown root:$2 /home/$2
# ssh remote origin group
sudo usermod -g $2 $1 
# remove user main directory[Symbolic link]
sudo ln -Tfs /home/$2/Public /var/www/html/$1
sudo mv /var/www/html/$1 /var/www/html/$2
# remove ssh group
groupdel $1
sudo deluser -remove-all-files $1
