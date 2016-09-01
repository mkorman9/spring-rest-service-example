package com.github.mkorman9.logic;

import com.github.mkorman9.logic.data.CatData;
import com.github.mkorman9.logic.data.CatsGroupData;
import com.github.mkorman9.model.Cat;
import com.github.mkorman9.model.CatsGroup;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class CatsPersistenceTestHelper {
    private CatsPersistenceTestHelper() {
    }

    static CatData createCatDataMock(String roleName, String name, int duelsWon, long groupId) {
        CatsGroupData groupDataMock = createGroupDataMock(groupId);

        CatData catData = mock(CatData.class);
        when(catData.getRoleName()).thenReturn(roleName);
        when(catData.getName()).thenReturn(name);
        when(catData.getGroup()).thenReturn(groupDataMock);
        when(catData.getDuelsWon()).thenReturn(duelsWon);
        return catData;
    }

    static CatsGroupData createGroupDataMock(long groupId) {
        CatsGroupData groupData = mock(CatsGroupData.class);
        when(groupData.getId()).thenReturn(groupId);
        return groupData;
    }

    static CatsGroup createCatsGroup(long id, String name) {
        CatsGroup group = new CatsGroup();
        group.setId(id);
        group.setName(name);
        return group;
    }

    static Cat createCat(long id, String roleName, String name, int duelsWon, CatsGroup group) {
        Cat cat = new Cat();
        cat.setId(id);
        cat.setRoleName(roleName);
        cat.setName(name);
        cat.setDuelsWon(duelsWon);
        cat.setGroup(group);
        return cat;
    }
}
