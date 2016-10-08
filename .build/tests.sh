#!/bin/bash

# Install test dependencies\
python -c "import pip" &> /dev/null
if [ $? != 0 ]; then
    curl -o /tmp/get-pip.py https://bootstrap.pypa.io/get-pip.py
    python /tmp/get-pip.py --user
fi

python -c "import robot" &> /dev/null
if [ $? != 0 ]; then
    python -m pip install --user robotframework
fi

python -c "import HttpLibrary" &> /dev/null
if [ $? != 0 ]; then
    python -m pip install --user robotframework-httplibrary
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

python -m robot -d $CI_WORK_DIRECTORY ${TRAVIS_BUILD_DIR}/automated-tests/*.robot

if [ $? != 0 ]; then
    exit 1
fi

exit 0
