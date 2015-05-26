#!/bin/sh
echo "extract playbook"
tar -xf  /opt/PlexView/comoam/playbook.tar -C /opt/PlexView/comoam/

if [ -f /opt/PlexView/comoam/datasource.tar.gz ]; then
    echo "restore data"
    tar -xzvf /opt/PlexView/comoam/datasource.tar.gz  -C / 
fi
echo "start COM LCM"
mkdir /opt/PlexView/comoam/server/logs
chmod +x /opt/PlexView/comoam/server/bin/*sh
/opt/PlexView/comoam/server/bin/startup.sh
bootrc=$(grep comoam /etc/rc.local)
if [ -z "$bootrc" ]; then
    echo "/opt/PlexView/comoam/server/bin/startup.sh" >>/etc/rc.local
fi
ssh-keygen -f id_rsa -t rsa -N ''
