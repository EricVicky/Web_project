#!/bin/sh
#cd /opt/PlexView/comoam/playbook/playbooks/kvm
#ln -s ../../inventory inventory
#ln -s ../../group_vars group_vars
#ln -s ../../roles roles
#ln -s ../../cfg/kvm.cfg ansible.cfg
#
#cd /opt/PlexView/comoam/playbook/playbooks/openstack
#ln -s ../../inventory inventory
#ln -s ../../group_vars group_vars
#ln -s ../../roles roles
#ln -s ../../cfg/openstack.cfg ansible.cfg
echo "unzip playbook"
tar -xvf  /opt/PlexView/comoam/playbook.tar -C /opt/PlexView/comoam/

echo "start COM LCM"
chmod +x /opt/PlexView/comoam/server/bin/*sh
/opt/PlexView/comoam/server/bin/startup.sh
