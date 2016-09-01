package com.github.mkorman9.logic;

import com.github.mkorman9.dao.CatsGroupRepository;
import com.github.mkorman9.logic.data.CatsGroupData;
import com.github.mkorman9.model.Cat;
import com.github.mkorman9.model.CatsGroup;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Set;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CatsGroupLogicTest {
    @Mock
    private CatsGroupRepository catsGroupRepository;
    @Mock
    private CatFactory catFactory;
    @InjectMocks
    private CatsGroupLogic catsGroupLogic;

    @Test
    public void allGroupsFromDBShouldBeFoundAndConvertedToData() throws Exception {
        // given
        CatsGroup testCatsGroup = CatsPersistenceTestHelper.createCatsGroup(0, "Pirates");
        CatsGroupData testCatsGroupData = CatsPersistenceTestHelper.createGroupDataMock(0);

        when(catsGroupRepository.findAll()).thenReturn(ImmutableList.of(testCatsGroup));
        when(catFactory.convertEntityGroupToData(eq(testCatsGroup))).thenReturn(testCatsGroupData);

        // when
        catsGroupLogic.findAll();

        // then
        verify(catsGroupRepository).findAll();
        verify(catFactory).convertEntityGroupToData(eq(testCatsGroup));
    }
}
