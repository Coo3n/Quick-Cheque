#!/bin/sh

curl -X POST -H 'Content-Type: Application/json' -d '{"email": "admin@example.com", "username": "admin", "password": "admin123"}' 192.168.0.77:8080/api/register
