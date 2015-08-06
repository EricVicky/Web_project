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

    def update(self, ip, iptype, status):
	    comstacks, comstack = self.search(ip, iptype)
	    if comstack != None:
	    	comstack['status'] = status 
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
   try:
      opts, args = getopt.getopt(argv,"hf:a:t:",["file=", "ip=", "iptype="])
   except getopt.GetoptError:
       error('grstatus.py -f <comstack.json> -s <stack_name>')
   for opt, arg in opts:
      if opt == '-h':
       	 error('grstatus.py -f <comstack.json> -s <stack_name>')
         sys.exit()
      elif opt in ("-t", "--iptype"):
         iptype = arg
      elif opt in ("-f", "--file"):
         comstackfile = arg
      elif opt in ("-a", "--ip"):
	 print arg
         ip = arg
   try:
	if ip == '' or ip is None:
		sys.exit(0)
	comstack = COMStack( comstackfile )
        comstack.update(ip,iptype, GRINSTALLED)
   except Exception:
      traceback.print_exc(file=sys.stdout)
      error("failed to register comstack")

if __name__ == "__main__":
   main(sys.argv[1:])
