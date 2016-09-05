package com.github.mkorman9.logic;

import com.github.mkorman9.logic.data.CatDto;
import com.github.mkorman9.logic.data.CatsGroupDto;
import com.github.mkorman9.model.Cat;
import com.github.mkorman9.model.CatsGroup;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class CatsPersistenceTestHelper {
    private CatsPersistenceTestHelper() {
    }

    static CatDto createCatDtoMock(String roleName, String name, int duelsWon, long groupId) {
        CatsGroupDto groupDataMock = createGroupDtoMock(groupId);

        CatDto catDto = mock(CatDto.class);
        when(catDto.getRoleName()).thenReturn(roleName);
        when(catDto.getName()).thenReturn(name);
        when(catDto.getGroup()).thenReturn(groupDataMock);
        when(catDto.getDuelsWon()).thenReturn(duelsWon);
        return catDto;
    }

    static CatsGroupDto createGroupDtoMock(long groupId) {
        CatsGroupDto groupData = mock(CatsGroupDto.class);
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
