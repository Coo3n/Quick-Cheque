#!/bin/sh

URL='http://192.168.0.77:8080/api/handle-workflow'

TOKEN=$(./get_token.sh)

BLOCK_1="\"http_request\": [\"https://catfact.ninja/fact\", \"GET\",\"\",\"\"]"
BLOCK_2="\"dummy_parser\": []"
BLOCK_3="\"save_to_db\": [\"user=dbuser password=password dbname=dbuser host=container-db\", \"cat_facts\"]"

curl -X POST -H 'Content-Type: application/json' -d "{\"token\": \"$TOKEN\", \"workflow\": { $BLOCK_1, $BLOCK_2, $BLOCK_3 }}" "$URL" | grep '"uuid":' | cut -f4 -d\"
