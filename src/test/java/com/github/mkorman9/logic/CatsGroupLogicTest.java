package com.github.mkorman9.logic;

import com.github.mkorman9.logic.data.CatsGroupData;
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
        catsGroupLogic = new CatsGroupLogic(catsGroupRepository, new CatFactory(catsGroupRepository));
    }

    @Test
    public void allGroupsShouldBeFound() throws Exception {
        // given
        Set<CatsGroupData> allGroups = catsGroupLogic.findAll();

        // then
        assertThat(allGroups.size()).isEqualTo(testGroups.size());
        assertThat(allGroups.stream()
                .filter(groupData -> groupData.getId() == 1L &&
                        groupData.getName().equals("Bandits"))
                .count())
                .isEqualTo(1);
        assertThat(allGroups.stream()
                .filter(groupData -> groupData.getId() == 2L &&
                        groupData.getName().equals("Pirates"))
                .count())
                .isEqualTo(1);
    }

    private Set<CatsGroup> createTestGroups() {
        return Sets.newHashSet(createCatsGroup(1L, "Bandits"), createCatsGroup(2L, "Pirates"));
    }

    private Set<Cat> createTestCats() {
        return Sets.newHashSet();
    }
}
