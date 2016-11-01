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


def check_if_cat_exists(cat_name):
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
    payload = {"roleName": "Bandit", "name": "Marcel", "duelsWon": 13, "group":
        {"id": download_group_id('Bandits')}}
    context.payload = payload
    context.response = add_cat(payload)


@then('it should be remembered')
def step_impl(context):
    assert check_if_cat_exists(context.payload['name'])
    cat_data = read_cat_data(find_cat_id_by_name(context.payload['name']))
    assert cat_data['name'] == context.payload['name']
    assert cat_data['roleName'] == context.payload['roleName']
    assert cat_data['duelsWon'] == context.payload['duelsWon']
    assert cat_data['group']['id'] == context.payload['group']['id']


@when('new cat request is sent with invalid data')
def step_impl(context):
    payload = {"name": "Invalid", "duelsWon": 1, "group": {"id": download_group_id('Bandits')}}
    context.payload = payload
    context.response = add_cat(payload)


@then('new validation error should be returned')
def step_impl(context):
    assert context.response.json()['status'] == 'error'
    assert not check_if_cat_exists(context.payload['name'])


@when('existing cat is deleted')
def step_impl(context):
    add_payload = {"roleName": "Pirate", "name": "Wojtek", "duelsWon": 2, "group": {"id": download_group_id('Pirates')}}
    context.payload = add_payload
    add_cat(add_payload)

    delete_cat(find_cat_id_by_name('Wojtek'))


@then('it should not exist in registry anymore')
def step_impl(context):
    assert not check_if_cat_exists(context.payload['name'])


@when('request of deleting cat with non-existing id is sent')
def step_impl(context):
    context.response = delete_cat(666)


@then('deleting error about non-existing cat should be returned')
def step_impl(context):
    assert context.response.status_code == 400


@when('existing cat is updated with new data')
def step_impl(context):
    pirates_id = download_group_id('Pirates')
    add_payload = {"roleName": "Pirate", "name": "Jack", "duelsWon": 4, "group": {"id": pirates_id}}
    add_cat(add_payload)
    context.id = find_cat_id_by_name('Jack')

    new_payload = {"roleName": "Chief Pirate", "name": "Jacky", "duelsWon": 5, "group": {"id": pirates_id}}
    context.new_payload = new_payload
    update_cat(context.id, new_payload)


@then('cat should be updated in registry')
def step_impl(context):
    cat_data = read_cat_data(context.id)
    assert cat_data['id'] == context.id
    assert cat_data['name'] == context.new_payload['name']
    assert cat_data['roleName'] == context.new_payload['roleName']
    assert cat_data['duelsWon'] == context.new_payload['duelsWon']
    assert cat_data['group']['id'] == context.new_payload['group']['id']


@when('request of updating data of non-existing cat is sent')
def step_impl(context):
    new_payload = {"roleName": "Chief Pirate", "name": "Jacky", "duelsWon": 5, "group": {"id": download_group_id('Pirates')}}
    context.response = update_cat(666, new_payload)


@then('updating error about non-existing cat should be returned')
def step_impl(context):
    assert context.response.status_code == 400


@when('request of updating data in invalid format is sent')
def step_impl(context):
    pirates_id = download_group_id('Pirates')
    add_payload = {"roleName": "Pirate", "name": "Michael", "duelsWon": 7, "group": {"id": pirates_id}}
    context.old_payload = add_payload
    add_cat(add_payload)
    context.id = find_cat_id_by_name('Michael')

    new_payload = {"name": "a", "duelsWon": -1, "group": {"id": 666}}
    context.response = update_cat(context.id, new_payload)


@then('updating error about invalid data should be returned')
def step_impl(context):
    assert context.response.json()['status'] == 'error'
    assert check_if_cat_exists(context.old_payload['name'])
