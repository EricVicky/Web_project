#/bin/sh
export HWADDR=$(cat /etc/sysconfig/network-scripts/ifcfg-eth0 |grep -oP '(?<=HWADDR=).*$')
export IPADDR=$(cat /etc/sysconfig/network-scripts/ifcfg-eth0 |grep -oP '(?<=IPADDR=).*$')
export NETMASK=$(cat /etc/sysconfig/network-scripts/ifcfg-eth0 |grep -oP '(?<=NETMASK=).*$')
export GATEWAY=$(cat /etc/sysconfig/network-scripts/ifcfg-eth0 |grep -oP '(?<=GATEWAY=).*$')

echo HWADDR=$HWADDR
echo IPADDR=$IPADDR
echo NETMASK=$NETMASK
echo GATEWAY=$GATEWAY