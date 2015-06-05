#!/bin/sh
if [ $# -lt 2 ]; then
   echo "enter both eth device and bridge name, examples:"
   echo "./config_bridge.sh eth0 br0"
   exit 1
fi
eth=$1
br=$2
HWADDR=$(cat /etc/sysconfig/network-scripts/ifcfg-eth0 |grep -oP '(?<=HWADDR=).*$')
IPADDR=$(cat /etc/sysconfig/network-scripts/ifcfg-eth0 |grep -oP '(?<=IPADDR=).*$')
NETMASK=$(cat /etc/sysconfig/network-scripts/ifcfg-eth0 |grep -oP '(?<=NETMASK=).*$')
GATEWAY=$(cat /etc/sysconfig/network-scripts/ifcfg-eth0 |grep -oP '(?<=GATEWAY=).*$')
PREFIX=$(cat /etc/sysconfig/network-scripts/ifcfg-eth0 |grep -oP '(?<=PREFIX=).*$')
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
ansible-playbook -i ../comoam-playbook/inventory/hosts.local -e "$VARS"  $PRGDIR/../comoam-playbook/playbooks/kvm/install_bridge.yml
