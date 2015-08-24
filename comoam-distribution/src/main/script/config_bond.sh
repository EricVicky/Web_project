#!/bin/bash

real_exec_user=`/usr/bin/id -un`
[ "${runner}" = "" ] && runner=root

if [ "${real_exec_user}" != "${runner}" ]; then
   echo " This script must be run by ${runner} user"
   exit 1
fi

function usage {
   echo "usage:"
   echo "    ./config_bond.sh -b bond -u ethx -e ethy"
   echo "    ./config_bond.sh -h"
   echo "option:"
   echo "    -b create a new bonding channel named bond"
   echo "    -u use the ip address in ethx as the ip address for bond"
   echo "       and add this NIC device ethx to the bonding channel"
   echo "    -e add NIC device ethy to the bonding channel"
   echo "example:"
   echo "    ./config_bond.sh -b bond0 -u eth0 -e eth5"
   echo "    ./config_bond.sh -b bond1 -u eth1 -e eth6"
}

if [ $# -eq 0 -o $# -lt 6 ]; then
 usage
 exit 0
fi

addr_itf=""
bond_itf=""
bond_slave_itf=""

TARGET_HOST="127.0.0.1"

while [[ $# > 0 ]]; do
  case $1 in
    -u)
      addr_itf="$2"
      shift 2
    ;;
    -b)
      bond_itf="$2"
      shift 2
    ;;
    -e)
      bond_slave_itf="$2"
      shift 2
    ;;
    -h)
      usage
      exit
    ;;
    *)
      usage
      exit
    ;;
  esac
done

if [ $# -ne 0 ]; then
 usage
 exit 0
fi

if [ -z "$bond_itf" -o -z "$addr_itf" -o -z "$bond_slave_itf" ]; then
 usage
 exit 1
fi

if [ "$bond_itf" == "$addr_itf" -o "$bond_itf" == "$bond_slave_itf" -o "$addr_itf" == "$bond_slave_itf" ]; then
 echo "bond, ethx and ethy should not be same."
 exit 1
fi

# resolve links - $0 may be a softlink
PRG="$0"

while [ -h "$PRG" ] ; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

PRGDIR=`dirname "$PRG"`
VARS="addr_itf=$addr_itf bond_itf=${bond_itf} bond_slave_itf=${bond_slave_itf}"

export host_IP=$TARGET_HOST
INVENTORY=$PRGDIR/./host
MATCH_LOCAL_IP=$(ifconfig -a|grep $TARGET_HOST)

if [ -z "$TARGET_HOST" ] || [ "$TARGET_HOST" == '127.0.0.1' ] || [ "$TARGET_HOST" == 'localhost' ] || [ ! -z "$MATCH_LOCAL_IP" ]; then
	echo 'setup bond for localhost'
	INVENTORY=$PRGDIR/../ELCM-playbook/inventory/hosts.local
fi

ansible-playbook -i $INVENTORY -e "$VARS"  $PRGDIR/../ELCM-playbook/playbooks/kvm/create_bond.yml 
