#!/bin/sh

curl -X POST -H 'Content-Type: Application/json' -d '{"email": "admin@example.com", "username": "admin", "password": "admin123"}' localhost:8080/api/register
