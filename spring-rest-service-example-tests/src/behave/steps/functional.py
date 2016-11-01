from behave import *
import os
import requests

SINGLE_CAT_ENDPOINT = 'http://localhost:{0}/cats/get/'.format(os.environ['APPLICATION_PORT'])
ALL_CATS_ENDPOINT = 'http://localhost:{0}/cats/all'.format(os.environ['APPLICATION_PORT'])
ALL_GROUPS_ENDPOINT = 'http://localhost:{0}/groups/all'.format(os.environ['APPLICATION_PORT'])
ADD_CAT_ENDPOINT = 'http://localhost:{0}/cats/add'.format(os.environ['APPLICATION_PORT'])
DELETE_CAT_ENDPOINT = 'http://localhost:{0}/cats/delete/'.format(os.environ['APPLICATION_PORT'])
UPDATE_CAT_ENDPOINT = 'http://localhost:{0}/cats/edit/'.format(os.environ['APPLICATION_PORT'])


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


@when('new cat is added')
def step_impl(context):
    cat_data = {"roleName": "Bandit", "name": "Marcel", "duelsWon": 13, "group": {"id": download_group_id('Bandits')}}
    context.remembered_cat_data = cat_data
    context.response = add_cat(cat_data)


@then('it should be remembered')
def step_impl(context):
    assert check_if_cat_exists_by_name(context.remembered_cat_data['name'])

    actual_cat_data = read_cat_data(find_cat_id_by_name(context.remembered_cat_data['name']))
    assert actual_cat_data['name'] == context.remembered_cat_data['name']
    assert actual_cat_data['roleName'] == context.remembered_cat_data['roleName']
    assert actual_cat_data['duelsWon'] == context.remembered_cat_data['duelsWon']
    assert actual_cat_data['group']['id'] == context.remembered_cat_data['group']['id']


@when('new cat request is sent with invalid data')
def step_impl(context):
    invalid_cat_data = {"name": "Invalid", "duelsWon": 1, "group": {"id": download_group_id('Bandits')}}
    context.remembered_cat_data = invalid_cat_data
    context.response = add_cat(invalid_cat_data)


@then('new validation error should be returned')
def step_impl(context):
    assert context.response.json()['status'] == 'error'
    assert not check_if_cat_exists_by_name(context.remembered_cat_data['name'])


@when('existing cat is deleted')
def step_impl(context):
    cat_data = {"roleName": "Pirate", "name": "Wojtek", "duelsWon": 2, "group": {"id": download_group_id('Pirates')}}
    context.remembered_cat_data = cat_data
    add_cat(cat_data)

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
    context.updated_cat_data = updated_cat_data
    update_cat(context.added_cat_id, updated_cat_data)


@then('cat should be updated in registry')
def step_impl(context):
    actual_cat_data = read_cat_data(context.added_cat_id)
    assert actual_cat_data['id'] == context.added_cat_id
    assert actual_cat_data['name'] == context.updated_cat_data['name']
    assert actual_cat_data['roleName'] == context.updated_cat_data['roleName']
    assert actual_cat_data['duelsWon'] == context.updated_cat_data['duelsWon']
    assert actual_cat_data['group']['id'] == context.updated_cat_data['group']['id']


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
    context.remembered_cat_data = cat_data
    add_cat(cat_data)

    added_cat_id = find_cat_id_by_name('Michael')
    invalid_cat_data = {"name": "a", "duelsWon": -1, "group": {"id": 666}}
    context.response = update_cat(added_cat_id, invalid_cat_data)


@then('updating error about invalid data should be returned and data should not be modified')
def step_impl(context):
    assert context.response.json()['status'] == 'error'
    assert check_if_cat_exists_by_name(context.remembered_cat_data['name'])

    actual_cat_data = read_cat_data(find_cat_id_by_name(context.remembered_cat_data['name']))
    assert actual_cat_data['name'] == context.remembered_cat_data['name']
    assert actual_cat_data['roleName'] == context.remembered_cat_data['roleName']
    assert actual_cat_data['duelsWon'] == context.remembered_cat_data['duelsWon']
    assert actual_cat_data['group']['id'] == context.remembered_cat_data['group']['id']
