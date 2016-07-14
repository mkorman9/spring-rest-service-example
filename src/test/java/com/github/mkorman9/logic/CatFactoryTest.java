package com.github.mkorman9.logic;

import com.github.mkorman9.logic.data.CatData;
import com.github.mkorman9.logic.data.CatsGroupData;
import com.github.mkorman9.model.Cat;
import com.github.mkorman9.model.CatsGroup;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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
        CatsGroupData catsGroupData = mock(CatsGroupData.class);
        when(catsGroupData.getId()).thenReturn(1L);
        CatData catData = mock(CatData.class);
        when(catData.getRoleName()).thenReturn("Pirate");
        when(catData.getName()).thenReturn("Barnaba");
        when(catData.getGroup()).thenReturn(catsGroupData);
        when(catData.getDuelsWon()).thenReturn(12);

        Cat cat = catFactory.createEntity(catData);

        assertThat(cat.getRoleName()).isEqualTo("Pirate");
        assertThat(cat.getName()).isEqualTo("Barnaba");
        assertThat(cat.getDuelsWon()).isEqualTo(12);
        assertThat(cat.getGroup().getId()).isEqualTo(1L);
        assertThat(cat.getGroup().getName()).isEqualTo("Pirates");
    }

    @Override
    protected List<CatsGroup> createTestGroups() {
        CatsGroup group1 = new CatsGroup();
        group1.setId(1L);
        group1.setName("Pirates");

        return Lists.newArrayList(group1);
    }

    @Override
    protected List<Cat> createTestCats() {
        return Lists.newArrayList();
    }
}
