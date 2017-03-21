from behave import *
import os
import requests


ENDPOINT_BASE = 'http://localhost:{0}'.format(os.environ['APPLICATION_PORT'])

SINGLE_CAT_ENDPOINT = '{0}/cats/get/'.format(ENDPOINT_BASE)
ALL_CATS_ENDPOINT   = '{0}/cats/all/'.format(ENDPOINT_BASE)
ALL_GROUPS_ENDPOINT = '{0}/groups/all/'.format(ENDPOINT_BASE)
ADD_CAT_ENDPOINT    = '{0}/cats/add/'.format(ENDPOINT_BASE)
DELETE_CAT_ENDPOINT = '{0}/cats/delete/'.format(ENDPOINT_BASE)
UPDATE_CAT_ENDPOINT = '{0}/cats/edit/'.format(ENDPOINT_BASE)


def download_group_id(group_name):
    all_groups = requests.get(ALL_GROUPS_ENDPOINT).json()['data']
    selected_group = filter(lambda record: record['name'] == group_name, all_groups)
    if len(selected_group) != 1:
        raise Exception('Group {0} not found'.format(group_name))
    return selected_group[0]['id']


def check_if_cat_exists_by_name(cat_name):
    all_cats = requests.get(ALL_CATS_ENDPOINT).json()['data']
    selected_cats = filter(lambda record: record['name'] == cat_name, all_cats)
    return len(selected_cats) == 1


def find_cat_id_by_name(cat_name):
    all_cats = requests.get(ALL_CATS_ENDPOINT).json()['data']
    selected_cats = filter(lambda record: record['name'] == cat_name, all_cats)
    return selected_cats[0]['id']


def read_cat_data(id):
    return requests.get(SINGLE_CAT_ENDPOINT + str(id)).json()['data']


def add_cat(cat_data):
    return requests.post(ADD_CAT_ENDPOINT, json=cat_data)


def delete_cat(cat_id):
    return requests.delete(DELETE_CAT_ENDPOINT + str(cat_id))


def update_cat(cat_id, cat_data):
    return requests.put(UPDATE_CAT_ENDPOINT + str(cat_id), json=cat_data)


def add_cat_and_remember_its_data(cat_data, context):
    context.remembered_cat_data = cat_data
    context.response = add_cat(cat_data)


def compare_remembered_cat_data_to_actual_state(context):
    actual_cat_data = read_cat_data(find_cat_id_by_name(context.remembered_cat_data['name']))
    assert actual_cat_data['name'] == context.remembered_cat_data['name']
    assert actual_cat_data['roleName'] == context.remembered_cat_data['roleName']
    assert actual_cat_data['duelsWon'] == context.remembered_cat_data['duelsWon']
    assert actual_cat_data['group']['id'] == context.remembered_cat_data['group']['id']


def validation_error_has_been_returned(context, expected_field, expected_message):
    response_data = context.response.json()
    for error in response_data['error']:
        if error['field'] == expected_field:
            return error['message'] == expected_message
    return False


@given('valid cat data')
def step_impl(context):
    context.cat_data = {"roleName": "Bandit", "name": "Marcel", "duelsWon": 13,
                        "group": {"id": download_group_id('Bandits')}}


@when('new cat endpoint is invoked')
def step_impl(context):
    add_cat_and_remember_its_data(context.cat_data, context)


@then('new cat should be saved')
def step_impl(context):
    assert check_if_cat_exists_by_name(context.remembered_cat_data['name'])
    compare_remembered_cat_data_to_actual_state(context)


@given('invalid cat data')
def step_impl(context):
    context.cat_data = {"name": "Invalid", "duelsWon": 1, "group": {"id": download_group_id('Bandits')}}


@then('new cat validation error should be returned')
def step_impl(context):
    assert context.response.json()['status'] == 'error'
    assert not check_if_cat_exists_by_name(context.remembered_cat_data['name'])


@when('existing cat is deleted')
def step_impl(context):
    cat_data = {"roleName": "Pirate", "name": "Wojtek", "duelsWon": 2, "group": {"id": download_group_id('Pirates')}}
    add_cat_and_remember_its_data(cat_data, context)

    delete_cat(find_cat_id_by_name('Wojtek'))


@then('it should not exist in registry anymore')
def step_impl(context):
    assert not check_if_cat_exists_by_name(context.remembered_cat_data['name'])


@when('request of deleting cat with non-existing id is sent')
def step_impl(context):
    context.response = delete_cat(666)


@then('deleting error about non-existing cat should be returned')
def step_impl(context):
    assert context.response.status_code == 400


@when('existing cat is updated with new data')
def step_impl(context):
    pirates_group_id = download_group_id('Pirates')
    cat_data = {"roleName": "Pirate", "name": "Jack", "duelsWon": 4, "group": {"id": pirates_group_id}}
    add_cat(cat_data)

    context.added_cat_id = find_cat_id_by_name('Jack')

    updated_cat_data = {"roleName": "Chief Pirate", "name": "Jacky", "duelsWon": 5, "group": {"id": pirates_group_id}}
    add_cat_and_remember_its_data(cat_data, context)
    update_cat(context.added_cat_id, updated_cat_data)


@then('cat should be updated in registry')
def step_impl(context):
    compare_remembered_cat_data_to_actual_state(context)


@when('request of updating data of non-existing cat is sent')
def step_impl(context):
    cat_data = {"roleName": "Chief Pirate", "name": "Jacky", "duelsWon": 5, "group": {"id": download_group_id('Pirates')}}
    context.response = update_cat(666, cat_data)


@then('updating error about non-existing cat should be returned')
def step_impl(context):
    assert context.response.status_code == 400


@when('request of updating data in invalid format is sent')
def step_impl(context):
    cat_data = {"roleName": "Pirate", "name": "Michael", "duelsWon": 7, "group": {"id": download_group_id('Pirates')}}
    add_cat_and_remember_its_data(cat_data, context)

    added_cat_id = find_cat_id_by_name('Michael')
    invalid_cat_data = {"name": "a", "duelsWon": -1, "group": {"id": 666}}
    context.response = update_cat(added_cat_id, invalid_cat_data)


@then('updating error about invalid data should be returned and data should not be modified')
def step_impl(context):
    assert context.response.json()['status'] == 'error'
    assert check_if_cat_exists_by_name(context.remembered_cat_data['name'])

    compare_remembered_cat_data_to_actual_state(context)
    assert validation_error_has_been_returned(context, 'duelsWon', 'Min')
    assert validation_error_has_been_returned(context, 'roleName', 'NotNull')
    assert validation_error_has_been_returned(context, 'name', 'Size')
