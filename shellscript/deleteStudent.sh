#! /bin/bash
sudo deluser -remove-all-files $1
sudo rm -rf /home/$1
sudo rm -rf /var/www/html/$1
