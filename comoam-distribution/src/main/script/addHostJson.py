#!/usr/bin/python
import json
import sys
with open('/opt/PlexView/ELCM/datasource/hosts.json', 'r') as hostsjson:
    try:
        feeds = json.load(hostsjson)
    except ValueError: 
        feeds = []

with open('/opt/PlexView/ELCM/datasource/hosts.json', 'w') as hostsjson:
	entry = {'ip_address': sys.argv[1], 'name': sys.argv[2]}
	for host in feeds:
		if entry['name'] == host['name']:
			host['name'] = entry['name']
			host['ip_address'] = entry['ip_address']
			json.dump(feeds, hostsjson, indent=2)
			sys.exit(0)
	feeds.append(entry)
	json.dump(feeds, hostsjson, indent=2)
