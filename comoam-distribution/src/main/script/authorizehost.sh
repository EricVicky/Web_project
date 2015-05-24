#!/bin/sh

#This script add SSH authorize key to remote server 
#and add host info into json file


/bin/echo "Please input hostname"
read host
/bin/echo "Please input IP address"
read IP

if [ -f ./authorhost.yml ]
then
  /bin/rm -f ./authorhost.yml
fi

/bin/sed -e "s/IP_address/$IP/g" ./authorhost.yml.template > ./authorhost.yml

ansible-playbook --ask-pass authorhost.yml

if [ $? = 0 ]
then
  /bin/echo "add authorized key to $IP successfully, now update json file"
else
  /bin/echo "add authorized key to $IP failed"
  exit 1
fi

/usr/bin/python ./addHostJson.py $host $IP

if [ $? = 0 ]
then
  /bin/echo "add to host json file successfully"
else
  /bin/echo "add to host json file failed"
  exit 1
fi

/bin/echo "successfully finished"
