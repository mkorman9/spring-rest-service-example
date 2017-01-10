package com.github.mkorman9.logic;

import com.github.mkorman9.dao.CatsGroupRepository;
import com.github.mkorman9.entity.Cat;
import com.github.mkorman9.entity.CatsGroup;
import com.github.mkorman9.logic.model.CatModel;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.github.mkorman9.logic.CatsPersistenceTestHelper.*;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.eq;
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
    public void shouldCreateEntityFromDto() throws Exception {
        // given
        String roleName = "Pirate";
        String name = "Barnaba";
        int duelsWon = 12;
        CatModel catModel = createCatModelMock(roleName, name, duelsWon, groupId);

        // when
        Cat cat = catFactory.createEntity(catModel);

        // then
        assertThat(cat.getRoleName()).isEqualTo(roleName);
        assertThat(cat.getName()).isEqualTo(name);
        assertThat(cat.getDuelsWon()).isEqualTo(duelsWon);
        assertThat(cat.getGroup().getId()).isEqualTo(groupId);
        assertThat(cat.getGroup().getName()).isEqualTo(groupName);
    }

    @Test
    public void shouldCreateDtoFromEntity() throws Exception {
        // given
        String roleName = "Pirate";
        String name = "Barnaba";
        int duelsWon = 12;
        Cat entity = createCat(1L, roleName, name, duelsWon, catsGroup);

        // when
        CatModel model = catFactory.createModel(entity);

        // then
        assertThat(model.getRoleName()).isEqualTo(roleName);
        assertThat(model.getName()).isEqualTo(name);
        assertThat(model.getDuelsWon()).isEqualTo(duelsWon);
        assertThat(model.getGroup().getId()).isEqualTo(groupId);
        assertThat(model.getGroup().getName()).isEqualTo(groupName);
    }

    @Test
    public void shouldThrowExceptionWhenUsingNonExistingGroup() throws Exception {
        // given
        String roleName = "Pirate";
        String name = "Barnaba";
        int duelsWon = 12;
        Long nonExistingGroupId = 10L;
        CatModel model = createCatModelMock(roleName, name, duelsWon, nonExistingGroupId);

        expectedException.expect(IllegalStateException.class);
        
        // when
        catFactory.createEntity(model);
    }
}
