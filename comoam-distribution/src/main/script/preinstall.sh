#!/bin/sh
echo "start install....."
INSTALL_ROOT=/opt/PlexView/ELCM
OLD_INSTALL_ROOT=/opt/PlexView/comoam
if [  -d $OLD_INSTALL_ROOT ]; then
   mv $OLD_INSTALL_ROOT $INSTALL_ROOT    
fi

