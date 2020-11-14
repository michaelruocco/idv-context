#!/usr/bin/env sh

dynamoUrl="http://dynamo-db:8000"

identityJson=$(cat /tmp/identity.json)
aws dynamodb --endpoint-url=$dynamoUrl create-table --cli-input-json "$identityJson"

contextJson=$(cat /tmp/context.json)
aws dynamodb --endpoint-url=$dynamoUrl create-table --cli-input-json "$contextJson"

attemptJson=$(cat /tmp/attempt.json)
aws dynamodb --endpoint-url=$dynamoUrl create-table --cli-input-json "$attemptJson"