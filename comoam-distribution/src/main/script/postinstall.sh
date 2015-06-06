#!/bin/sh
echo "extract playbook"
tar -xf  /opt/PlexView/comoam/playbook.tar -C /opt/PlexView/comoam/

if [ -f /opt/PlexView/comoam/datasource.tar.gz ]; then
    echo "restore data"
    tar -xzf /opt/PlexView/comoam/datasource.tar.gz  -C / 
fi
echo "install jpam"
tar -xzf /usr/lib/jvm/jre-1.7.0-openjdk.x86_64/lib/amd64/libjpam.so.tar.gz -C /usr/lib/jvm/jre-1.7.0-openjdk.x86_64/lib/amd64/
echo "start COM ELCM"
if [ ! -d /opt/PlexView/comoam/server/logs ]; then
    mkdir /opt/PlexView/comoam/server/logs
fi
if [ ! -d /opt/PlexView/comoam/workspace ]; then
    mkdir /opt/PlexView/comoam/workspace
fi

if [ ! -d /opt/PlexView/comoam/comoam-playbook/hot_files ]; then
    mkdir /opt/PlexView/comoam/comoam-playbook/hot_files 
fi
chmod +x /opt/PlexView/comoam/server/bin/*sh
/opt/PlexView/comoam/server/bin/startup.sh
bootrc=$(grep comoam /etc/rc.local)
if [ -z "$bootrc" ]; then
    echo "/opt/PlexView/comoam/server/bin/startup.sh" >>/etc/rc.local
fi
if [ ! -f ~/.ssh/id_rsa ]; then
    echo "generate ssh key"
    ssh-keygen -f ~/.ssh/id_rsa -t rsa -N '' -q
fi
echo 'Installation of ELCM tool completed!'
echo 'Access COM ELCM from the link http://HOST_IP_ADDRESS/ as axadmin user'
