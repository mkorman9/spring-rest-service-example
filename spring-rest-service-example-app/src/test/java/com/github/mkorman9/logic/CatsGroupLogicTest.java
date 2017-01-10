package com.github.mkorman9.logic;

import com.github.mkorman9.dao.CatsGroupRepository;
import com.github.mkorman9.entity.CatsGroup;
import com.github.mkorman9.logic.model.CatsGroupModel;
import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
        CatsGroupModel testCatsGroupModel = CatsPersistenceTestHelper.createGroupModelMock(0);

        when(catsGroupRepository.findAll()).thenReturn(ImmutableList.of(testCatsGroup));
        when(catFactory.convertGroupEntityToModel(eq(testCatsGroup))).thenReturn(testCatsGroupModel);

        // when
        catsGroupLogic.findAll();

        // then
        verify(catsGroupRepository).findAll();
        verify(catFactory).convertGroupEntityToModel(eq(testCatsGroup));
    }
}
