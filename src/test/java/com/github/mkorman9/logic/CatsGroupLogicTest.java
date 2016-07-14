package com.github.mkorman9.logic;

import com.github.mkorman9.model.Cat;
import com.github.mkorman9.model.CatsGroup;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class CatsGroupLogicTest extends CatsPersistenceTestHelper {
    private CatsGroupLogic catsGroupLogic;

    private CatsGroup group1, group2;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        catsGroupLogic = new CatsGroupLogic(catsGroupRepository);
    }

    @Test
    public void allGroupsShouldBeFound() throws Exception {
        List<CatsGroup> allGroups = catsGroupLogic.findAll();

        assertThat(Sets.newHashSet(allGroups)).isEqualTo(Sets.newHashSet(group1, group2));
    }

    @Override
    protected List<CatsGroup> createTestGroups() {
        group1 = new CatsGroup();
        group1.setId(1L);
        group1.setName("Bandits");

        group2 = new CatsGroup();
        group2.setId(2L);
        group2.setName("Pirates");

        return Lists.newArrayList(group1, group2);
    }

    @Override
    protected List<Cat> createTestCats() {
        return Lists.newArrayList();
    }
}
