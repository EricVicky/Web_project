#!/bin/sh

real_exec_user=`/usr/bin/id -un`
[ "${runner}" = "" ] && runner=root
if [ "${real_exec_user}" != "${runner}" ]; then
   echo " This script must be run by ${runner} user"
   exit 1
fi

if [ $# -lt 2 ]; then
   echo "enter both eth device and bridge name, examples:"
   echo "./config_bridge.sh eth0 br0"
   exit 1
fi
eth=$1
br=$2
if [ ! -f /etc/sysconfig/network-scripts/ifcfg-"$eth" ] ; then
    echo "$eth not found"
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
VARS="eth=$eth br=$br HWADDR=$HWADDR IPADDR=$IPADDR NETMASK=$NETMASK GATEWAY=$GATEWAY PREFIX=$PREFIX"
ansible-playbook -i ../ELCM-playbook/inventory/hosts.local -e "$VARS"  $PRGDIR/../ELCM-playbook/playbooks/kvm/install_bridge.yml
