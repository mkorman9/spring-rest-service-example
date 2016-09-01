#!/bin/bash

# Install test dependencies
$PIP_BINARY install robotframework-httplibrary

# Wait until application wakes up
timeout_counter="0"
while [ "$(curl -s "http://127.0.0.1:$APPLICATION_PORT/")" == '' ]; do
    if [[ $timeout_counter == "10" ]]; then
        echo "Application wake up phase timeout out"
        exit 1
    fi

    sleep 2
    timeout_counter=$[$timeout_counter+1]
done

$ROBOT_BINARY ${WORKSPACE}/automated-tests/*.robot

if [ $? != 0 ]; then
    exit 1
fi

exit 0
