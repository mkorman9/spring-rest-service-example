package com.github.mkorman9.logic;

import com.github.mkorman9.logic.data.CatData;
import com.github.mkorman9.logic.testhelper.CatsPersistenceTestHelper;
import com.github.mkorman9.model.Cat;
import com.github.mkorman9.model.CatsGroup;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.Set;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class CatLogicTest extends CatsPersistenceTestHelper {
    private CatLogic catLogic;

    private List<CatsGroup> testGroups;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        testGroups = createTestGroups();
        super.setUp(testGroups, createTestCats());

        catLogic = new CatLogic(catRepository, new CatFactory(catsGroupRepository));
    }

    @Test
    public void shouldPersistNewCat() throws Exception {
        // given
        CatData catToAdd = createCatDataMock(1L, "Pirate", "Barnaba", 13);

        // when
        catLogic.addNewCat(catToAdd);
        Set<CatData> allCats = catLogic.findAllCats();

        // then
        assertThat(allCats.size()).isEqualTo(4);
        assertThat(allCats.stream()
                .filter(cat -> cat.getRoleName().equals("Pirate") &&
                        cat.getName().equals("Barnaba") &&
                        cat.getDuelsWon() == 13
                        )
                .count())
                .isEqualTo(1);
    }

    @Test
    public void shouldEditExistingCat() throws Exception {
        // given
        CatData catDataToUpdate = createCatDataMock(1L, "Pirate", "Barnaba", 13);

        // when
        catLogic.updateCat(1L, catDataToUpdate);
        Set<CatData> allCats = catLogic.findAllCats();

        // then
        assertThat(allCats.size()).isEqualTo(3);
        assertThat(allCats.stream()
                .filter(cat -> cat.getName().equals("Barnaba") &&
                        cat.getRoleName().equals("Pirate") &&
                        cat.getDuelsWon().equals(13) &&
                        cat.getGroup().getId().equals(1L))
                .count())
                .isEqualTo(1);
    }

    @Test
    public void shouldDeleteExistingCat() throws Exception {
        // when
        catLogic.removeCat(1L);
        Set<CatData> allCats = catLogic.findAllCats();

        // then
        assertThat(allCats.size()).isEqualTo(2);
        assertThat(allCats.stream()
                .filter(cat -> cat.getName().equals("Jack") &&
                        cat.getRoleName().equals("Pirate") &&
                        cat.getDuelsWon().equals(10) &&
                        cat.getGroup().getId().equals(1L))
                .count())
                .isEqualTo(0);
    }

    @Test
    public void shouldThrowWhenCatNotFound() throws Exception {
        // given
        expectedException.expect(IllegalArgumentException.class);

        // then
        catLogic.findSingleCat(1000L);
    }

    @Test
    public void shouldThrowWhenUpdatingUnavailableCat() throws Exception {
        // given
        expectedException.expect(IllegalArgumentException.class);

        // then
        catLogic.updateCat(1000L, mock(CatData.class));
    }

    private List<CatsGroup> createTestGroups() {
        return Lists.newArrayList(createCatsGroup(1L, "Pirates"),
                createCatsGroup(2L, "Bandits"));
    }

    private List<Cat> createTestCats() {
        return Lists.newArrayList(createCat(1L, "Pirate", "Jack", 10, testGroups.get(0)),
                createCat(2L, "Bandit", "Bonny", 7, testGroups.get(1)),
                createCat(3L, "Bandit", "Clyde", 7, testGroups.get(1)));
    }
}
