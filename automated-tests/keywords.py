import json

def cat_should_exist_on_list(cats_json, role_name, name, duels_won, group_name):
     cats = json.loads(cats_json)
     exists = False
     for cat in cats:
        if cat['roleName'] == role_name and cat['name'] == name and cat['duelsWon'] == duels_won and cat['group']['name'] == group_name:
            exists = True
     if exists == False:
        raise AssertionError('Specified cat (%s, %s, %d, %s) does not exists on list %s' % (role_name, name, duels_won, group_name, cats_json))
