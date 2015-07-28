#!/bin/bash

real_exec_user=`/usr/bin/id -un`
[ "${runner}" = "" ] && runner=root
if [ "${real_exec_user}" != "${runner}" ]; then
   echo " This script must be run by ${runner} user"
   exit 1
fi

function usage {
   echo "usage:"
   echo "    ./config_bridge.sh [-s] [--6s] [-b|-v] itf br"
   echo "    ./config_bridge.sh -h"
   echo "option:"
   echo "    itf source interface device"
   echo "    br destination bridge device, br0, br1 .. is preferred."
   echo "    -s don't use the IPv4 gateway in if as the default IPv4 route"
   echo "    --6s don't use the IPv6 gateway in if as the default IPv6 route, if IPv6 is present in itf"
   echo "    -b the source interface is a bonding channel"
   echo "    -v the source interface is a vlan on bonding channel"
   echo "    -h display help message"
   echo "example:"
   echo "    ./config_bridge.sh --host <HOST_IP>  eth0 br0"
   echo "    ./config_bridge.sh --host <HOST_IP> -s eth1 br1"
   echo "    ./config_bridge.sh --host <HOST_IP> -s --6s eth1 br1"
   echo "    ./config_bridge.sh --host <HOST_IP> -b bond0 br0"
   echo "    ./config_bridge.sh --host <HOST_IP> -s -b bond1 br1"
   echo "    ./config_bridge.sh --host <HOST_IP> --6s -b bond1 br1"
   echo "    ./config_bridge.sh --host <HOST_IP> -v bond0.190 br0"
}

if [ $# -eq 0 ]; then
 usage
 exit 0
fi

PRIMARY="true"
PRIMARY6="true"
itf_type="eth"
TARGET_HOST="127.0.0.1"
while [[ $# > 0 ]]; do
  case $1 in
    --host)
      TARGET_HOST=$2
      shift 2
    ;;
    -s)
      PRIMARY="false"
      shift
    ;;
    --6s)
      PRIMARY6="false"
      shift
    ;;
    -b)
      itf_type="bond"
      shift
    ;;
    -v)
      itf_type="vlan"
      shift
    ;;
    -h)
      usage
      exit
    ;;
    *)
      eth=$1
      br=$2
      shift 2
      break
    ;;
  esac
done

if [ $# -ne 0 ]; then
 usage
 exit 0
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
VARS="itf=$eth br=$br PRIMARY=${PRIMARY} PRIMARY6=${PRIMARY6} itf_type=${itf_type}"
export host_IP=$TARGET_HOST
ansible-playbook -i $PRGDIR/./host -e "$VARS"  $PRGDIR/../ELCM-playbook/playbooks/kvm/install_bridge.yml 
