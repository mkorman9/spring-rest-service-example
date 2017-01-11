#!/bin/bash

function print_logs() {
    echo '================================================='
    docker logs $APP_NAME
    echo '================================================='
}

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
while true; do
    status_code=$(curl -s -o /dev/null -w "%{http_code}" "http://127.0.0.1:$APPLICATION_PORT/")
    echo "Trying http://127.0.0.1:$APPLICATION_PORT/... ${status_code}"

    if [ "$status_code" == "200" ] || [ "$status_code" == "404" ]; then
        break
    fi

    if [[ $timeout_counter == "20" ]]; then
        echo "Application wake up phase timed out"
        print_logs
        exit 1
    fi

    sleep 2
    timeout_counter=$[$timeout_counter+1]
done

python -m behave spring-rest-service-example-tests/src/behave

print_logs

if [ $? != 0 ]; then
    exit 1
fi

exit 0
