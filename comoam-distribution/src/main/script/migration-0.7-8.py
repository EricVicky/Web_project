#!/usr/bin/python
import json
import sys
from math import *
comstackfile="/opt/PlexView/ELCM/datasource/comstack.json"

def migvmconfig(vm_config):
	for vm in vm_config:
		cfg = vm_config[vm]
		if not cfg.has_key('ip_address') :
			continue
		nic = [{ "name": "eth0", "bridge": "br0", "ip_v4": {}}] 
		nic[0]['ip_v4']['ipaddress'] = cfg['ip_address'];
		nic[0]['ip_v4']['gateway'] = cfg['gateway'];
		nic[0]['ip_v4']['prefix'] = nm2prefix(cfg['netmask']);
		del cfg['ip_address']
		del cfg['gateway']
		del cfg['netmask']
		if cfg.has_key('v6_ip_addr') :
			nic[0]['ip_v6'] = {}
			nic[0]['ip_v6']['ipaddress'] = cfg['v6_ip_addr'];
			nic[0]['ip_v6']['gateway'] = cfg['v6_gateway'];
			nic[0]['ip_v6']['prefix'] = cfg['v6_prefix'];
			del cfg['v6_ip_addr']
			del cfg['v6_gateway']
			del cfg['v6_prefix']
		cfg['nic'] = nic

def nm2prefix(netmask):
	smasks = netmask.split('.')	
	prefix = 0
	for submask in smasks:
		submaskInt = int(submask)	
		prefix = prefix + (8 - int(log(256-submaskInt,2)))
	return prefix

def output(comstacks):
	with open(comstackfile, 'w') as comstacksfs:
    		json.dump(comstacks, comstacksfs,  indent=2)

with open(comstackfile, 'r') as comstacksfs:
    try:
        comstacks = json.load(comstacksfs)
        for comstack in comstacks:
		if comstack['comType'] == 'FCAPS' or  comstack['comType'] == 'CM' or comstack['comType'] == 'OAM' :
			comConfig = json.loads(comstack['comConfig'])
			vm_config = comConfig['vm_config']	
			migvmconfig(vm_config)		

			comstack['comConfig'] = json.dumps(comConfig)
	output(comstacks)
    except ValueError:
        print 'failed to load comstacks'
