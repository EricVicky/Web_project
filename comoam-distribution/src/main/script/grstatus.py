#!/usr/bin/python

import sys, getopt,traceback
import os.path
import yaml
import json
import time


def error(message):
    sys.stderr.write("error: %s\n" % message)
    sys.exit(1)


class COMStack():
    def __init__(self, file ):
            self.comstackfile = file 

    def update(self, pri_ip, sec_ip, iptype, status, role):
	    comstacks_pri, pri_comstack = self.search(pri_ip, iptype)
	    comstacks_sec, sec_comstack = self.search(sec_ip, iptype)
	    mate = None
	    comstack = None
	    if role == 'Primary':
	        mate = sec_comstack['name']
	        comstack = pri_comstack
		comstacks = comstacks_pri
	    elif role == 'Secondary':
	        mate = pri_comstack['name']
	        comstack = sec_comstack
		comstacks = comstacks_sec
	    if comstack != None and comstacks != None:
	    	comstack['status'] = status 
	    	comstack['role'] = role
	    	comstack['mate'] = mate
	    	with open(self.comstackfile, 'w') as comstacksfs:
    			json.dump(comstacks, comstacksfs,  indent=2)

    def loadcomstacks(self):
        with open(self.comstackfile, 'r') as comstacksfs:
            try:
                comstacks = json.load(comstacksfs)
                return comstacks
            except ValueError:
	    	error("can not load comstack.json")
                return None 
        return None
	
    def search(self, ip, iptype):
        comstacks = self.loadcomstacks()
	if comstacks == None:
	    error("can not load comstack.json")
	    return
	for comstack in comstacks:
		comConfig = json.loads(comstack['comConfig'])
		nics = comConfig['vm_config']['oam']['nic']
		if nics is None:
	    		error("No nic attribute")
		for nic in nics:
			if iptype == 'ipv4' and  nic['ip_v4']  and  nic['ip_v4']['ipaddress'] == ip : 
				return (comstacks, comstack)
			if iptype == 'ipv6' and  nic['ip_v6']  and  nic['ip_v6']['ipaddress'] == ip : 
				return (comstacks, comstack)
        return (comstacks, None)

def error(message):
    sys.stderr.write("error: %s\n" % message)
    sys.exit(0)
        
def main(argv):
   comstackfile="/opt/PlexView/ELCM/datasource/comstack.json"
   ip = ''
   iptype = 'ipv4'
   GRINSTALLED="GRINSTALLED" 
   role="Primary" 
   pri_ip=None
   sec_ip=None
   try:
      opts, args = getopt.getopt(argv,"hf:p:s:t:r:",["file=", "pri_ip=", "sec_ip=","iptype=","role="])
   except getopt.GetoptError:
       error('grstatus.py -p <pri_ip> -s <sec_ip> -f <comstack.json> -r  <role> -t <ip_type>')
   for opt, arg in opts:
      if opt == '-h':
       	 error('grstatus.py -p <pri_ip> -s <sec_ip> -f <comstack.json> -r <role> -t <ip_type>')
         sys.exit()
      elif opt in ("-t", "--iptype"):
         iptype = arg
      elif opt in ("-f", "--file"):
         comstackfile = arg
      elif opt in ("-r", "--role"):
         role = arg
      elif opt in ("-s", "--sec_ip"):
         sec_ip = arg
      elif opt in ("-p", "--pri_ip"):
         pri_ip = arg
   try:
	if pri_ip == '' or pri_ip is None or  sec_ip == '' or sec_ip is None:
		sys.exit(0)
	comstack = COMStack( comstackfile )
        comstack.update(pri_ip,sec_ip, iptype, GRINSTALLED,role)
   except Exception:
      error("failed to register comstack")
      traceback.print_exc(file=sys.stdout)

if __name__ == "__main__":
   main(sys.argv[1:])
