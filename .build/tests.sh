#!/bin/bash

# Simple smoke test. Will be replaced with full test suite
echo $(curl -s "http://127.0.0.1:$APPLICATION_PORT/all")
STATUS=$(curl -s "http://127.0.0.1:$APPLICATION_PORT/all" | python -c 'import json,sys;obj=json.load(sys.stdin);print obj["status"]')
if [ "$STATUS" != 'ok' ]; then
    echo $STATUS
    exit 1
fi

exit 0
