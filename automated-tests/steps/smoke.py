from behave import *
import os
import requests


@given('application is running')
def step_impl(context):
    pass


@when('sending request to valid endpoint')
def step_impl(context):
    address = 'http://localhost:{0}/cats/all'.format(os.environ['APPLICATION_PORT'])
    context.response = requests.get(address)


@when('sending request to not-existing endpoint')
def step_impl(context):
    address = 'http://localhost:{0}/not_existing'.format(os.environ['APPLICATION_PORT'])
    context.response = requests.get(address)


@then('status 200 should be returned with valid json response')
def step_impl(context):
    parsed_response = context.response.json()
    assert context.response.status_code == 200
    assert 'status' in parsed_response


@then('status 404 should be returned with valid json response')
def step_impl(context):
    parsed_response = context.response.json()
    assert context.response.status_code == 404
    assert 'status' in parsed_response
