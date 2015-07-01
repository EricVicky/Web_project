#!/bin/sh
PRO_COMOAM=$(ps -ef|grep 'opt/PlexView/ELCM'|grep -v grep)
if [ ! -z "$PRO_COMOAM" ]; then
    echo "Stop ELCM"
    /opt/PlexView/ELCM/server/bin/shutdown.sh
    sleep 5 
fi
rm -f /opt/PlexView/ELCM/datasource.tar.gz
echo "backup data"
tar -czvf /opt/PlexView/ELCM/datasource.tar.gz  /opt/PlexView/ELCM/datasource/*
