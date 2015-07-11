#!/bin/sh

#This script add SSH authorize key to remote server 
#and add host info into json file

real_exec_user=`/usr/bin/id -un`
[ "${runner}" = "" ] && runner=root
if [ "${real_exec_user}" != "${runner}" ]; then
   echo " This script must be run by ${runner} user"
   exit 1
fi


/bin/echo "Please input hostname:"
read host
if [ -z $host ]; then
    echo "host name requried!"
    exit 1
fi
/bin/echo "Please input IP address:"
read IP

if [ -z $IP ]; then
    echo "host IP required!"
    exit 1
fi

export host_IP=$IP
ansible-playbook -i host  --ask-pass ../ELCM-playbook/playbooks/kvm/authhost.yml

if [ $? = 0 ]
then
  /bin/echo "add authorized key to $IP successfully, now update json file"
else
  /bin/echo "add authorized key to $IP failed"
  exit 1
fi

/usr/bin/python ./addHostJson.py $IP $host 

if [ $? = 0 ]
then
  /bin/echo "add to host json file successfully"
else
  /bin/echo "add to host json file failed"
  exit 1
fi

/bin/echo "successfully finished"
