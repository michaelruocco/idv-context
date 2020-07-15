#!/usr/bin/env bash

json=$(cat /opt/tables/identity.json)
awslocal dynamodb create-table --cli-input-json "$json"