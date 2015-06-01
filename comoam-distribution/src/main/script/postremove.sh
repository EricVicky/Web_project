#!/bin/sh
echo "remove playbook"
rm -rf /opt/PlexView/comoam/comoam-playbook
echo "remove server"
rm -rf /opt/PlexView/comoam/server
echo "remove script"
rm -rf /opt/PlexView/comoam/script
#cat /etc/rc.local|grep -v '/opt/PlexView/comoam/' > /etc/rc.local
sed -i '/\/opt\/PlexView\/comoam.*$/d' /etc/rc.local
echo "uninstall completed!"
