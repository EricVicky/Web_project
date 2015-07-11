#!/usr/bin/python

import sys, getopt,traceback
import os.path
import yaml
import json
import time

class Usage(Exception):
    def __init__(self, msg):
            self.msg = msg

def error(message):
    sys.stderr.write("error: %s\n" % message)
    sys.exit(1)


class COMStack():

    comstackfile="/opt/PlexView/ELCM/datasource/comstack.json"

    def __init__(self, comType=None, name=None, status='STANDALONE', **kwargs ):
            self.comType = comType
            self.name = name
            self.status = status
            self.updatedate = int(round(time.time() * 1000))

    def setComconfig(self, comConfig):
            self.comConfig = comConfig

    def append(self):
            comstacks = self.loadcomstacks()
            comstacks.append(self.__dict__)
            print comstacks
	    with open(self.comstackfile, 'w') as comstacksfs:
    		json.dump(comstacks, comstacksfs,  indent=2)

    def loadcomstacks(self):
        with open(self.comstackfile, 'r') as comstacksfs:
            try:
                comstacks = json.load(comstacksfs)
                return comstacks
            except ValueError:
                error('failed to load comstacks')
        return None

def error(message):
    sys.stderr.write("error: %s\n" % message)
    sys.exit(1)

def reg(varfilename):
    if os.path.exists(varfilename):
        try:
            with open(varfilename, 'r') as stream:
                comConfig = yaml.load(stream)
                comStack = COMStack(comConfig['comType'], comConfig['deployment_prefix'])
                comStack.setComconfig(json.dumps(comConfig))
                comStack.append()
        except Exception:
            raise Usage('failed to  save stack')
#            error("failed to open the file {0}".format(varfilename))
    else:
            error("failed to open the file {0}".format(varfilename))
            raise Usage('failed to  save stack')
        
def main(argv):
   varfilename = ''
   try:
      opts, args = getopt.getopt(argv,"hv:",["vfile="])
   except getopt.GetoptError:
      raise Usage('regcomstack.py -v <group_var/all>')
   for opt, arg in opts:
      if opt == '-h':
         Usage('regcomstack.py -v <group_var/all>')
         sys.exit()
      elif opt in ("-v", "--vfile"):
         varfilename = arg
   try:
      reg(varfilename)
   except Exception:
      error("failed to register comstack")
      traceback.print_exc(file=sys.stdout)


if __name__ == "__main__":
   main(sys.argv[1:])
