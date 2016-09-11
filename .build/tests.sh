#!/bin/bash

# Install test dependencies
python -c "import HttpLibrary" &> /dev/null
if [ $? != 0 ]; then
    $PIP_BINARY install --user robotframework-httplibrary
fi

# Wait until application wakes up
timeout_counter="0"
while [ "$(curl -s "http://127.0.0.1:$APPLICATION_PORT/")" == '' ]; do
    if [[ $timeout_counter == "10" ]]; then
        echo "Application wake up phase timed out"
        exit 1
    fi

    sleep 2
    timeout_counter=$[$timeout_counter+1]
done

$ROBOT_BINARY -d $CI_WORK_DIRECTORY ${WORKSPACE}/automated-tests/*.robot

if [ $? != 0 ]; then
    exit 1
fi

exit 0
