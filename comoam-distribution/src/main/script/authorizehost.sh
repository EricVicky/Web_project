#!/bin/sh

#This script add SSH authorize key to remote server
#and add host info into json file

dir_util=`dirname $0`
tmp_host=${dir_util}/tmp_host_$$
real_exec_user=`/usr/bin/id -un`
[ "${runner}" = "" ] && runner=root
if [ "${real_exec_user}" != "${runner}" ]; then
   echo " This script must be run by ${runner} user"
   exit 1
fi

if [ "$#" -ne "0" ] && [ "$#" -ne "3" ]; then
   echo "Usage: authorizehost.sh [host_name] [host_ip] [root_password]"
   exit 1;
fi

if [ "$#" = "0" ]; then
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
  /bin/echo "Password for root user:"
  ansible-playbook -i host  --ask-pass ${dir_util}/../ELCM-playbook/playbooks/kvm/authhost.yml
else
  host="$1"
  IP="$2"
  pass="$3"

  echo "[host]" > ${tmp_host}
  echo "${IP} ansible_ssh_user=root ansible_ssh_pass=${pass}" >> ${tmp_host}

  ansible-playbook -i ${tmp_host} ${dir_util}/../ELCM-playbook/playbooks/kvm/authhost.yml

  play_result=$?

  rm ${tmp_host}
fi

if [ ${play_result} = 0 ]
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

