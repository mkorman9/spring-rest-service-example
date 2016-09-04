package com.github.mkorman9.logic;

import com.github.mkorman9.dao.CatsGroupRepository;
import com.github.mkorman9.logic.data.CatData;
import com.github.mkorman9.logic.data.CatsGroupData;
import com.github.mkorman9.model.Cat;
import com.github.mkorman9.model.CatsGroup;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static com.github.mkorman9.logic.CatsPersistenceTestHelper.createCat;
import static com.github.mkorman9.logic.CatsPersistenceTestHelper.createCatDataMock;
import static com.github.mkorman9.logic.CatsPersistenceTestHelper.createCatsGroup;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CatFactoryTest {
    private final long groupId = 1L;
    private final String groupName = "Pirates";
    private final CatsGroup catsGroup = createCatsGroup(groupId, groupName);

    @Mock
    private CatsGroupRepository catsGroupRepository;
    @InjectMocks
    private CatFactory catFactory;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        when(catsGroupRepository.findOne(eq(groupId))).thenReturn(catsGroup);
    }

    @Test
    public void shouldCreateEntityFromData() throws Exception {
        // given
        String roleName = "Pirate";
        String name = "Barnaba";
        int duelsWon = 12;
        CatData catData = createCatDataMock(roleName, name, duelsWon, groupId);

        // when
        Cat cat = catFactory.createEntity(catData);

        // then
        assertThat(cat.getRoleName()).isEqualTo(roleName);
        assertThat(cat.getName()).isEqualTo(name);
        assertThat(cat.getDuelsWon()).isEqualTo(duelsWon);
        assertThat(cat.getGroup().getId()).isEqualTo(groupId);
        assertThat(cat.getGroup().getName()).isEqualTo(groupName);
    }

    @Test
    public void shouldCreateDataFromEntity() throws Exception {
        // given
        String roleName = "Pirate";
        String name = "Barnaba";
        int duelsWon = 12;
        Cat entity = createCat(1L, roleName, name, duelsWon, catsGroup);

        // when
        CatData data = catFactory.createData(entity);

        // then
        assertThat(data.getRoleName()).isEqualTo(roleName);
        assertThat(data.getName()).isEqualTo(name);
        assertThat(data.getDuelsWon()).isEqualTo(duelsWon);
        assertThat(data.getGroup().getId()).isEqualTo(groupId);
        assertThat(data.getGroup().getName()).isEqualTo(groupName);
    }

    @Test
    public void shouldThrowExceptionWhenUsingNonExistingGroup() throws Exception {
        // given
        String roleName = "Pirate";
        String name = "Barnaba";
        int duelsWon = 12;
        Long nonExistingGroupId = 10L;
        CatData data = createCatDataMock(roleName, name, duelsWon, nonExistingGroupId);

        expectedException.expect(IllegalStateException.class);
        
        // when
        catFactory.createEntity(data);
    }
}
