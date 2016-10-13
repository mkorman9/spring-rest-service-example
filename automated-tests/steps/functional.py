from behave import *
import os
import requests

@given('application is running')
def step_impl(context):
    pass


@when('new cat is added')
def step_impl(context):
    pass


@then('it should be remembered')
def step_impl(context):
    pass


@when('new cat request is sent with invalid data')
def step_impl(context):
    pass


@then('new validation error should be returned')
def step_impl(context):
    pass


@when('existing cat is deleted')
def step_impl(context):
    pass


@then('it should not exist in registry anymore')
def step_impl(context):
    pass


@when('request of deleting cat with non-existing id is sent')
def step_impl(context):
    pass


@then('deleting error about non-existing cat should be returned')
def step_impl(context):
    pass


@when('existing cat is updated with new data')
def step_impl(context):
    pass


@then('cat should be updated in registry')
def step_impl(context):
    pass


@when('request of updating data of non-existing cat is sent')
def step_impl(context):
    pass


@then('updating error about non-existing cat should be returned')
def step_impl(context):
    pass


@when('request of updating data in invalid format is sent')
def step_impl(context):
    pass


@then('updating error about invalid data should be returned')
def step_impl(context):
    pass
