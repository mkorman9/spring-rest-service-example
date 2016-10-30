from behave import *
import os
import requests

ALL_CATS_ENDPOINT = 'http://localhost:{0}/cats/all'.format(os.environ['APPLICATION_PORT'])
ALL_GROUPS_ENDPOINT = 'http://localhost:{0}/groups/all'.format(os.environ['APPLICATION_PORT'])
ADD_CAT_ENDPOINT = 'http://localhost:{0}/cats/add'.format(os.environ['APPLICATION_PORT'])
DELETE_CAT_ENDPOINT = 'http://localhost:{0}/cats/delete/'.format(os.environ['APPLICATION_PORT'])


def download_group_id(group_name):
    all_groups = requests.get(ALL_GROUPS_ENDPOINT).json()['data']
    selected_group = filter(lambda record: record['name'] == group_name, all_groups)
    if len(selected_group) != 1:
        raise Exception('Group {0} not found'.format(group_name))
    return selected_group[0]['id']


def check_if_cat_exists(cat_data):
    all_cats = requests.get(ALL_CATS_ENDPOINT).json()['data']
    selected_cats = filter(lambda record: record['name'] == cat_data['name'], all_cats)
    return len(selected_cats) == 1


def find_cat_id_by_name(cat_name):
    all_cats = requests.get(ALL_CATS_ENDPOINT).json()['data']
    selected_cats = filter(lambda record: record['name'] == cat_name, all_cats)
    return selected_cats[0]['id']


@when('new cat is added')
def step_impl(context):
    payload = {"roleName": "Bandit", "name": "Marcel", "duelsWon": 13, "group":
        {"id": download_group_id('Bandits')}}
    context.payload = payload
    context.response = requests.post(ADD_CAT_ENDPOINT, json=payload)


@then('it should be remembered')
def step_impl(context):
    assert check_if_cat_exists(context.payload)


@when('new cat request is sent with invalid data')
def step_impl(context):
    payload = {"name": "Invalid", "duelsWon": 1, "group": {"id": download_group_id('Bandits')}}
    context.payload = payload
    context.response = requests.post(ADD_CAT_ENDPOINT, json=payload)


@then('new validation error should be returned')
def step_impl(context):
    assert context.response.json()['status'] == 'error'
    assert not check_if_cat_exists(context.payload)


@when('existing cat is deleted')
def step_impl(context):
    add_payload = {"roleName": "Pirate", "name": "Wojtek", "duelsWon": 2, "group": {"id": download_group_id('Pirates')}}
    context.payload = add_payload
    requests.post(ADD_CAT_ENDPOINT, json=add_payload)
    added_cat_id = find_cat_id_by_name('Wojtek')

    requests.delete(DELETE_CAT_ENDPOINT + str(added_cat_id))


@then('it should not exist in registry anymore')
def step_impl(context):
    assert not check_if_cat_exists(context.payload)


@when('request of deleting cat with non-existing id is sent')
def step_impl(context):
    context.response = requests.delete(DELETE_CAT_ENDPOINT + '666')


@then('deleting error about non-existing cat should be returned')
def step_impl(context):
    assert context.response.status_code == 500


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
