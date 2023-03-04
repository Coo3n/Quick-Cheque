#!/bin/sh

URL='192.168.0.77:8080/api/login'

PASS=admin123
USER=admin

DATA="{\"username\": \"$USER\", \"password\": \"$PASS\"}"

curl -X POST -H 'Content-Type: application/json' -d "$DATA" "$URL" | grep '"token":' | cut -f 4 -d\"
