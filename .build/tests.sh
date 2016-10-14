#!/bin/bash

# Install test dependencies
python -c "import pip" &> /dev/null
if [ $? != 0 ]; then
    curl -o /tmp/get-pip.py https://bootstrap.pypa.io/get-pip.py
    python /tmp/get-pip.py --user
fi

python -c "import behave" &> /dev/null
if [ $? != 0 ]; then
    python -m pip install --user behave
fi

python -c "import requests" &> /dev/null
if [ $? != 0 ]; then
    python -m pip install --user requests
fi

# Wait until application wakes up
timeout_counter="0"
while [ "$(curl -s "http://127.0.0.1:$APPLICATION_PORT/")" == '' ]; do
    echo "Trying http://127.0.0.1:$APPLICATION_PORT/"
    if [[ $timeout_counter == "10" ]]; then
        echo "Application wake up phase timed out"
        exit 1
    fi

    sleep 2
    timeout_counter=$[$timeout_counter+1]
done

python -m behave spring-rest-service-example-tests/src/behave

if [ $? != 0 ]; then
    exit 1
fi

exit 0
