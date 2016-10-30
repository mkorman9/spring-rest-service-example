package com.github.mkorman9.logic;

import com.github.mkorman9.logic.dto.CatDto;
import com.github.mkorman9.logic.dto.CatsGroupDto;
import com.github.mkorman9.entity.Cat;
import com.github.mkorman9.entity.CatsGroup;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class CatsPersistenceTestHelper {
    private CatsPersistenceTestHelper() {
    }

    static CatDto createCatDtoMock(String roleName, String name, int duelsWon, long groupId) {
        CatsGroupDto groupDtoMock = createGroupDtoMock(groupId);

        CatDto catDto = mock(CatDto.class);
        when(catDto.getRoleName()).thenReturn(roleName);
        when(catDto.getName()).thenReturn(name);
        when(catDto.getGroup()).thenReturn(groupDtoMock);
        when(catDto.getDuelsWon()).thenReturn(duelsWon);
        return catDto;
    }

    static CatsGroupDto createGroupDtoMock(long groupId) {
        CatsGroupDto groupDto = mock(CatsGroupDto.class);
        when(groupDto.getId()).thenReturn(groupId);
        return groupDto;

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
