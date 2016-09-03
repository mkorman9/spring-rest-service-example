import json

def _compre_str(val1, val2):
    return str(val1) == str(val2)

def cat_should_exist_on_list(cats_json, role_name, name, duels_won, group_name):
     cats = json.loads(cats_json)
     exists = False
     for cat in cats:
        if _compre_str(cat['roleName'], role_name) and _compre_str(cat['name'], name) and _compre_str(cat['duelsWon'], duels_won) and _compre_str(cat['group']['name'], group_name):
            exists = True
     if exists == False:
        raise AssertionError('Specified cat (%s, %s, %s, %s) does not exists on list %s' % (role_name, name, duels_won, group_name, cats_json))
