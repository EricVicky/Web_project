#!/bin/sh
echo "remove playbook"
rm -rf /opt/PlexView/ELCM/ELCM-playbook
echo "remove server"
rm -rf /opt/PlexView/ELCM/server
echo "remove script"
rm -rf /opt/PlexView/ELCM/script
#cat /etc/rc.local|grep -v '/opt/PlexView/ELCM/' > /etc/rc.local
sed -i '/\/opt\/PlexView\/ELCM.*$/d' /etc/rc.local
rm -f /usr/lib/jvm/jre-1.7.0-openjdk.x86_64/lib/amd64/libjpam.so
echo "uninstall completed!"
