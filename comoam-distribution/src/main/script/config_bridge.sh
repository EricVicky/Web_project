#!/bin/sh

real_exec_user=`/usr/bin/id -un`
[ "${runner}" = "" ] && runner=root
if [ "${real_exec_user}" != "${runner}" ]; then
   echo " This script must be run by ${runner} user"
   exit 1
fi

function usage {
   echo "usage:"
   echo "    ./config_bridge.sh [-s] [-b|-v] if br"
   echo "    ./config_bridge.sh -h"
   echo "option:"
   echo "    if source interface device"
   echo "    br destination bridge device, br0, br1 .. is preferred."
   echo "    -s don't use the gateway in if as the default route"
   echo "    -b the source interface is a bonding channel"
   echo "    -v the source interface is a vlan on bonding channel"
   echo "    -h display help message"
   echo "example:"
   echo "    ./config_bridge.sh eth0 br0"
   echo "    ./config_bridge.sh -s eth1 br1"
   echo "    ./config_bridge.sh -b bond0 br0"
   echo "    ./config_bridge.sh -s -b bond1 br1"
   echo "    ./config_bridge.sh -v bond0.190 br0"
}

if [ $# -eq 0 ]; then
 usage
 exit 0
fi

PRIMARY="true"
itf_type="eth"

while [[ $# > 0 ]]; do
  case $1 in
    -s)
      PRIMARY="false"
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

if [ ! -f /etc/sysconfig/network-scripts/ifcfg-"$eth" ] ; then
    echo "$eth not found"
    exit 1
fi
if [ -f /etc/sysconfig/network-scripts/ifcfg-"$br" ] ; then
    echo "bridge $br exits."
    exit 1
fi
bridge=$(grep -i bridge /etc/sysconfig/network-scripts/ifcfg-"$eth")
if [ ! -z "$bridge" ]; then
    echo " the bridge on $eth has already been setup"
    exit 1
fi
HWADDR=$(cat /etc/sysconfig/network-scripts/ifcfg-"$eth" |grep -oP '(?<=HWADDR=).*$')
IPADDR=$(cat /etc/sysconfig/network-scripts/ifcfg-"$eth" |grep -oP '(?<=IPADDR=).*$')
NETMASK=$(cat /etc/sysconfig/network-scripts/ifcfg-"$eth" |grep -oP '(?<=NETMASK=).*$')
GATEWAY=$(cat /etc/sysconfig/network-scripts/ifcfg-"$eth" |grep -oP '(?<=GATEWAY=).*$')
PREFIX=$(cat /etc/sysconfig/network-scripts/ifcfg-"$eth" |grep -oP '(?<=PREFIX=).*$')
echo HWADDR=$HWADDR
echo IPADDR=$IPADDR
echo NETMASK=$NETMASK
echo GATEWAY=$GATEWAY
echo PREFIX=$PREFIX
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
VARS="itf=$eth br=$br HWADDR=$HWADDR IPADDR=$IPADDR NETMASK=$NETMASK GATEWAY=$GATEWAY PREFIX=$PREFIX PRIMARY=${PRIMARY} itf_type=${itf_type}"
ansible-playbook -i ../ELCM-playbook/inventory/hosts.local -e "$VARS"  $PRGDIR/../ELCM-playbook/playbooks/kvm/install_bridge.yml
