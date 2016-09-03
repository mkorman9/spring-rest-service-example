import json


def _compare_str(val1, val2):
    return str(val1) == str(val2)


def _compare_cat_properties(cat, role_name, name, duels_won, group_name):
    return _compare_str(cat['roleName'], role_name) and _compare_str(cat['name'], name) and _compare_str(cat['duelsWon'], duels_won) and _compare_str(cat['group']['name'], group_name)


def cat_should_be_equal_to(cat_json, role_name, name, duels_won, group_name):
    cat = json.loads(cat_json)
    if not _compare_cat_properties(cat, role_name, name, duels_won, group_name):
        raise AssertionError('Cat %s is not equal to (%s, %s, %s, %s)' % (cat_json, role_name, name, duels_won, group_name))


def find_id_by_name(entity_json, name):
    entities = json.loads(entity_json)
    for entity in entities:
        if _compare_str(entity['name'], name):
            return entity['id']
    raise AssertionError('Entity with name %s was not found in %s' % (name, entity_json))


def cat_should_exist_on_list(cats_json, role_name, name, duels_won, group_name):
     cats = json.loads(cats_json)
     exists = False
     for cat in cats:
        if _compare_cat_properties(cat, role_name, name, duels_won, group_name):
            exists = True
     if exists == False:
        raise AssertionError('Specified cat (%s, %s, %s, %s) does not exists on list %s' % (role_name, name, duels_won, group_name, cats_json))


def cat_should_not_exist_on_list(cats_json, role_name, name, duels_won, group_name):
     cats = json.loads(cats_json)
     exists = False
     for cat in cats:
        if _compare_cat_properties(cat, role_name, name, duels_won, group_name):
            exists = True
     if exists == True:
        raise AssertionError('Specified cat (%s, %s, %s, %s) does not exists on list %s' % (role_name, name, duels_won, group_name, cats_json))
