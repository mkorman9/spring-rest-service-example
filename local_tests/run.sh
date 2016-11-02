#!/bin/bash

# Just a small script to run automatic tests in developers environment
export APPLICATION_PORT=9000
bash .build/tests.sh
