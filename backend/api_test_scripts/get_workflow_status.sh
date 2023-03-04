#!/bin/sh

URL='http://192.168.0.77:8080/api/get-workflow-status'

TOKEN=$(./get_token.sh)

DATA="{\"token\": \"$TOKEN\", \"uuid\": \"$1\"}"

curl -X POST -H 'Content-Type: application/json' -d "$DATA" "$URL"
