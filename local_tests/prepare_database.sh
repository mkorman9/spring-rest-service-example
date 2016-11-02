#!/bin/bash

# Preparing clean local database for running automated tests in developer's environment
LOCAL_DATABASE_HOST=127.0.0.1
LOCAL_DATABASE_NAME=test
LOCAL_DATABASE_USER=root
LOCAL_DATABASE_PASS=root

function run_sql() {
    mysql --host=$LOCAL_DATABASE_HOST --user=$LOCAL_DATABASE_USER --password=$LOCAL_DATABASE_PASS -e "${1}" 2> /dev/null
}

run_sql 'drop database test' || { echo 'Cannot connect to database'; exit 1; }
run_sql 'create database test'

. .build/build.properties.sh
rm -rf target/.schema
git clone "${DEPLOY_SCHEMA_URL}" target/.schema

liquibase --driver=com.mysql.jdbc.Driver --changeLogFile=target/.schema/changelog.xml \
--url=jdbc:mysql://$LOCAL_DATABASE_HOST:3306/$LOCAL_DATABASE_NAME --username=$LOCAL_DATABASE_USER --password=$LOCAL_DATABASE_PASS update
