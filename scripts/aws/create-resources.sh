#!/bin/bash
set -x

mkdir -p ~/.aws

cat > ~/.aws/config << EOF
[default]
region = us-east-1
output = json
EOF

cat > ~/.aws/credentials << EOF
[default]
aws_access_key_id = x
aws_secret_access_key = x
EOF

aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name template-template_requested_dlq --output text
aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name template-template_requested --attributes '{"RedrivePolicy":"{\"maxReceiveCount\":\"1\",\"deadLetterTargetArn\":\"arn:aws:sqs:us-east-1:000000000000:template-template_requested_dlq\"}"}' --output text

aws --endpoint-url=http://localhost:4566 sns subscribe \
    --topic-arn $(aws --endpoint-url=http://localhost:4566 sns create-topic --name notification_v1 --output text) \
    --protocol sqs \
    --notification-endpoint $(aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name template-notification --output text) \
    --output text

set +x
