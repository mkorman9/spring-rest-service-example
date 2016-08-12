package com.github.mkorman9.logic;

import com.github.mkorman9.logic.data.CatData;
import com.github.mkorman9.logic.data.CatsGroupData;
import com.github.mkorman9.model.Cat;
import com.github.mkorman9.model.CatsGroup;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CatFactoryTest extends CatsPersistenceTestHelper {
    private CatFactory catFactory;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        catFactory = new CatFactory(catsGroupRepository);
    }

    @Test
    public void shouldCreateEntityFromData() throws Exception {
        CatsGroupData catsGroupData = prepareCatsGroupDataMock(1L);
        CatData catData = prepareCatDataMock("Pirate", "Barnaba", catsGroupData, 12);

        Cat cat = catFactory.createEntity(catData);

        assertThat(cat.getRoleName()).isEqualTo("Pirate");
        assertThat(cat.getName()).isEqualTo("Barnaba");
        assertThat(cat.getDuelsWon()).isEqualTo(12);
        assertThat(cat.getGroup().getId()).isEqualTo(1L);
        assertThat(cat.getGroup().getName()).isEqualTo("Pirates");
    }

    @Override
    protected Set<CatsGroup> createTestGroups() {
        CatsGroup group1 = new CatsGroup();
        group1.setId(1L);
        group1.setName("Pirates");

        return Sets.newHashSet(group1);
    }

    @Override
    protected Set<Cat> createTestCats() {
        return Sets.newHashSet();
    }

    private CatsGroupData prepareCatsGroupDataMock(Long id) {
        CatsGroupData catsGroupData = mock(CatsGroupData.class);
        when(catsGroupData.getId()).thenReturn(id);
        return catsGroupData;
    }
    private CatData prepareCatDataMock(String roleName, String name, CatsGroupData group, int duelsWon) {
        CatData catData = mock(CatData.class);
        when(catData.getRoleName()).thenReturn(roleName);
        when(catData.getName()).thenReturn(name);
        when(catData.getGroup()).thenReturn(group);
        when(catData.getDuelsWon()).thenReturn(duelsWon);
        return catData;
    }
}
