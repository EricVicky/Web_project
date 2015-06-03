#!/usr/bin/python
import json
import sys
with open('../datasource/hosts.json', 'r') as hostsjson:
    try:
        feeds = json.load(hostsjson)
    except ValueError: 
        feeds = []

with open('../datasource/hosts.json', 'w') as hostsjson:
    entry = {'ip_address': sys.argv[1], 'name': sys.argv[2]}
    feeds.append(entry)
    json.dump(feeds, hostsjson, indent=2)
