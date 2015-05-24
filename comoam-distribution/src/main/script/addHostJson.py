#!/usr/bin/python
import json
import sys
with open('../datasource/hosts.json', 'r') as feedsjson:
    try:
        feeds = json.load(feedsjson)
    except ValueError: 
        feeds = []

with open('../datasource/hosts.json', 'w') as feedsjson:
    entry = {'hostname': sys.argv[1], 'url': sys.argv[2]}
    feeds.append(entry)
    json.dump(feeds, feedsjson, indent=2)
