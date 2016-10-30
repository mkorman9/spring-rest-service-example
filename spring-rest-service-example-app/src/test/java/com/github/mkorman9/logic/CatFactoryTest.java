package com.github.mkorman9.logic;

import com.github.mkorman9.dao.CatsGroupRepository;
import com.github.mkorman9.entity.Cat;
import com.github.mkorman9.entity.CatsGroup;
import com.github.mkorman9.logic.dto.CatDto;
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
        CatDto catDto = createCatDtoMock(roleName, name, duelsWon, groupId);

        // when
        Cat cat = catFactory.createEntity(catDto);

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
        CatDto dto = catFactory.createDto(entity);

        // then
        assertThat(dto.getRoleName()).isEqualTo(roleName);
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getDuelsWon()).isEqualTo(duelsWon);
        assertThat(dto.getGroup().getId()).isEqualTo(groupId);
        assertThat(dto.getGroup().getName()).isEqualTo(groupName);
    }

    @Test
    public void shouldThrowExceptionWhenUsingNonExistingGroup() throws Exception {
        // given
        String roleName = "Pirate";
        String name = "Barnaba";
        int duelsWon = 12;
        Long nonExistingGroupId = 10L;
        CatDto dto = createCatDtoMock(roleName, name, duelsWon, nonExistingGroupId);

        expectedException.expect(IllegalStateException.class);
        
        // when
        catFactory.createEntity(dto);
    }
}
