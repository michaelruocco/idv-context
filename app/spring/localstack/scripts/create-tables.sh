#!/usr/bin/env bash

identityJson=$(cat /opt/tables/identity.json)
awslocal dynamodb create-table --cli-input-json "$identityJson"

attemptJson=$(cat /opt/tables/attempt.json)
awslocal dynamodb create-table --cli-input-json "$attemptJson"