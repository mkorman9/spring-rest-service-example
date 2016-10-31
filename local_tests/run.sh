#!/bin/bash

# Just a small script to run automatic tests in developers environment
export APPLICATION_PORT=9000

bash local_tests/prepare_database.sh || { echo 'ERROR'; exit 1; }

read -rsp $'Run application now and press any key to continue...\n' -n1 key

bash .build/tests.sh
