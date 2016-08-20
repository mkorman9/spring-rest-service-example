package com.github.mkorman9.logic;

import com.github.mkorman9.logic.testhelper.CatsPersistenceTestHelper;
import com.github.mkorman9.model.Cat;
import com.github.mkorman9.model.CatsGroup;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.fest.assertions.Assertions.assertThat;

public class CatsGroupLogicTest extends CatsPersistenceTestHelper {
    private CatsGroupLogic catsGroupLogic;

    private Set<CatsGroup> testGroups;

    @Before
    public void setUp() throws Exception {
        testGroups = createTestGroups();
        super.setUp(testGroups, createTestCats());
        catsGroupLogic = new CatsGroupLogic(catsGroupRepository);
    }

    @Test
    public void allGroupsShouldBeFound() throws Exception {
        // given
        Set<CatsGroup> allGroups = catsGroupLogic.findAll();

        // then
        assertThat(allGroups).containsOnly(testGroups.toArray());
    }

    private Set<CatsGroup> createTestGroups() {
        return Sets.newHashSet(createCatsGroup(1L, "Bandits"), createCatsGroup(2L, "Pirates"));
    }

    private Set<Cat> createTestCats() {
        return Sets.newHashSet();
    }
}
