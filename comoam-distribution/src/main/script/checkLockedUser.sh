#!/bin/sh
aUser=$1
failCount=$(/sbin/pam_tally2 --user ${aUser} | grep ${aUser} | awk '{print $2}')
if [ $failCount -gt 5 ]; then
   exit 1
fi
exit 0
