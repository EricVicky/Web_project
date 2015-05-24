#/bin/sh
export HWADDR=$(cat /etc/sysconfig/network-scripts/ifcfg-eth0 |grep -oP '(?<=HWADDR=).*$')
export IPADDR=$(cat /etc/sysconfig/network-scripts/ifcfg-eth0 |grep -oP '(?<=IPADDR=).*$')
export NETMASK=$(cat /etc/sysconfig/network-scripts/ifcfg-eth0 |grep -oP '(?<=NETMASK=).*$')
export GATEWAY=$(cat /etc/sysconfig/network-scripts/ifcfg-eth0 |grep -oP '(?<=GATEWAY=).*$')
echo HWADDR=$HWADDR
echo IPADDR=$IPADDR
echo NETMASK=$NETMASK
echo GATEWAY=$GATEWAY
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
VARS="eth=$eth br=$br HWADDR=$HWADDR IPADDR=$IPADDR NETMASK=$NETMASK GATEWAY=$GATEWAY"
ansible-playbook -i ../comoam-playbook/inventory/hosts.local -e "$VARS"  $PRGDIR/../comoam-playbook/playbooks/kvm/install_bridge.yml
