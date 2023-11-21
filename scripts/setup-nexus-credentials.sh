#!/bin/bash

AD_USER=$1
AD_PASSWD=$2

if [ -z "$AD_USER" ]
then
      echo "Parameter [0] AD user is null"
      exit 1
fi

if [ -z "$AD_PASSWD" ]
then
      echo "Parameter [1] AD PWD is null"
            exit 1
fi

cat > gradle.properties << EOF
nexusUsername=$AD_USER
nexusPassword=$AD_PASSWD
EOF
