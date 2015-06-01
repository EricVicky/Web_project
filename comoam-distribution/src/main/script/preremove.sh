#!/bin/sh
PRO_COMOAM=$(ps -ef|grep 'opt/PlexView/comoam'|grep -v grep)
if [ ! -z "$PRO_COMOAM" ]; then
    echo "Stop COM LCM"
    /opt/PlexView/comoam/server/bin/shutdown.sh
    sleep 5 
fi
rm -f /opt/PlexView/comoam/datasource.tar.gz
echo "backup data"
tar -czvf /opt/PlexView/comoam/datasource.tar.gz  /opt/PlexView/comoam/datasource/*
