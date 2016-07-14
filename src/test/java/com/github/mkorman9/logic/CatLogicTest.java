package com.github.mkorman9.logic;

import com.github.mkorman9.logic.data.CatData;
import com.github.mkorman9.logic.data.CatsGroupData;
import com.github.mkorman9.model.Cat;
import com.github.mkorman9.model.CatsGroup;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CatLogicTest extends CatsPersistenceTestHelper {
    private CatLogic catLogic;
    private CatFactory catFactory;

    private CatsGroup group1, group2;
    private Cat cat1, cat2, cat3;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        catFactory = new CatFactory(catsGroupRepository);
        catLogic = new CatLogic(catRepository, catFactory);
    }

    @Test
    public void shouldPersistCats() throws Exception {
        CatData catToAddData = createNewCatData();
        CatData catUpdatedData = createUpdatedCatData();
        Cat catToAdd = catFactory.createEntity(catToAddData);
        Cat cat2Updated = catFactory.createEntity(catUpdatedData);
        cat2Updated.setId(2L);

        List<Cat> catsFoundBefore = catLogic.findAllCats();
        catLogic.addNewCat(catToAddData);
        catLogic.removeCat(1L);
        catLogic.updateCat(2L, catUpdatedData);
        List<Cat> catsFoundAfter = catLogic.findAllCats();

        assertThat(Sets.newHashSet(catsFoundBefore)).isEqualTo(Sets.newHashSet(cat1, cat2, cat3));
        assertThat(Sets.newHashSet(catsFoundAfter)).isEqualTo(Sets.newHashSet(cat2Updated, cat3, catToAdd));
    }

    private CatData createNewCatData() {
        CatsGroupData groupData = mock(CatsGroupData.class);
        when(groupData.getId()).thenReturn(1L);

        CatData catData = mock(CatData.class);
        when(catData.getRoleName()).thenReturn("Pirate");
        when(catData.getName()).thenReturn("Barnaba");
        when(catData.getGroup()).thenReturn(groupData);
        when(catData.getDuelsWon()).thenReturn(12);

        return catData;
    }

    private CatData createUpdatedCatData() {
        CatsGroupData groupData = mock(CatsGroupData.class);
        when(groupData.getId()).thenReturn(2L);

        CatData catData = mock(CatData.class);
        when(catData.getRoleName()).thenReturn("Bandit");
        when(catData.getName()).thenReturn("Bonny");
        when(catData.getGroup()).thenReturn(groupData);
        when(catData.getDuelsWon()).thenReturn(8);

        return catData;
    }

    @Override
    protected List<CatsGroup> createTestGroups() {
        group1 = new CatsGroup();
        group1.setId(1L);
        group1.setName("Pirates");

        group2 = new CatsGroup();
        group2.setId(2L);
        group2.setName("Bandits");

        return Lists.newArrayList(group1, group2);
    }

    @Override
    protected List<Cat> createTestCats() {
        cat1 = new Cat();
        cat1.setId(1L);
        cat1.setRoleName("Pirate");
        cat1.setName("Jack");
        cat1.setDuelsWon(10);
        cat1.setGroup(group1);

        cat2 = new Cat();
        cat2.setId(2L);
        cat2.setRoleName("Bandit");
        cat2.setName("Bonny");
        cat2.setDuelsWon(7);
        cat2.setGroup(group2);

        cat3 = new Cat();
        cat3.setId(3L);
        cat3.setRoleName("Bandit");
        cat3.setName("Clyde");
        cat3.setDuelsWon(7);
        cat3.setGroup(group2);

        return Lists.newArrayList(cat1, cat2, cat3);
    }
}
