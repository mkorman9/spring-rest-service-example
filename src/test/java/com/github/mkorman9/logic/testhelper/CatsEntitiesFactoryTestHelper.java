package com.github.mkorman9.logic.testhelper;

import com.github.mkorman9.logic.data.CatData;
import com.github.mkorman9.logic.data.CatsGroupData;
import com.github.mkorman9.model.Cat;
import com.github.mkorman9.model.CatsGroup;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class CatsEntitiesFactoryTestHelper {
    protected CatData createCatDataMock(long groupId, String roleName, String name, int duelsWon) {
        CatsGroupData groupDataMock = createGroupDataMock(groupId);

        CatData catData = mock(CatData.class);
        when(catData.getRoleName()).thenReturn(roleName);
        when(catData.getName()).thenReturn(name);
        when(catData.getGroup()).thenReturn(groupDataMock);
        when(catData.getDuelsWon()).thenReturn(duelsWon);
        return catData;
    }

    protected CatsGroupData createGroupDataMock(long groupId) {
        CatsGroupData groupData = mock(CatsGroupData.class);
        when(groupData.getId()).thenReturn(groupId);
        return groupData;
    }

    protected CatsGroup createCatsGroup(long id, String name) {
        CatsGroup group = new CatsGroup();
        group.setId(id);
        group.setName(name);
        return group;
    }

    protected Cat createCat(long id, String roleName, String name, int duelsWon, CatsGroup group) {
        Cat cat = new Cat();
        cat.setId(id);
        cat.setRoleName(roleName);
        cat.setName(name);
        cat.setDuelsWon(duelsWon);
        cat.setGroup(group);
        return cat;
    }
}
