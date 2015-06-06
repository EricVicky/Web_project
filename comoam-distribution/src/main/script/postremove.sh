#!/bin/sh
echo "remove playbook"
rm -rf /opt/PlexView/comoam/comoam-playbook
echo "remove server"
rm -rf /opt/PlexView/comoam/server
echo "remove script"
rm -rf /opt/PlexView/comoam/script
#cat /etc/rc.local|grep -v '/opt/PlexView/comoam/' > /etc/rc.local
sed -i '/\/opt\/PlexView\/comoam.*$/d' /etc/rc.local
rm -f /usr/lib/jvm/jre-1.7.0-openjdk.x86_64/lib/amd64/libjpam.so
echo "uninstall completed!"
