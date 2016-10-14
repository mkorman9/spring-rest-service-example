package com.github.mkorman9.logic;

import com.github.mkorman9.dao.CatsGroupRepository;
import com.github.mkorman9.logic.dto.CatsGroupDto;
import com.github.mkorman9.model.CatsGroup;
import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
    public void allGroupsFromDBShouldBeFoundAndConvertedToDto() throws Exception {
        // given
        CatsGroup testCatsGroup = CatsPersistenceTestHelper.createCatsGroup(0, "Pirates");
        CatsGroupDto testCatsGroupDto = CatsPersistenceTestHelper.createGroupDtoMock(0);

        when(catsGroupRepository.findAll()).thenReturn(ImmutableList.of(testCatsGroup));
        when(catFactory.convertEntityGroupToDto(eq(testCatsGroup))).thenReturn(testCatsGroupDto);

        // when
        catsGroupLogic.findAll();

        // then
        verify(catsGroupRepository).findAll();
        verify(catFactory).convertEntityGroupToDto(eq(testCatsGroup));
    }
}
