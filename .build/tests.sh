#!/bin/bash

# Simple smoke test. Will be replaced with full test suite
timeout_counter="0"
while [ "$(curl -s "http://127.0.0.1:$APPLICATION_PORT/")" == '' ]; do
    if [[ $timeout_counter == "10" ]]; then
        break
    fi

    sleep 2
    timeout_counter=$[$timeout_counter+1]
done

STATUS=$(curl -s "http://127.0.0.1:$APPLICATION_PORT/all" | python -c 'import json,sys;obj=json.load(sys.stdin);print obj["status"]')
if [ "$STATUS" != 'ok' ]; then
    echo $STATUS
    exit 1
fi

exit 0
