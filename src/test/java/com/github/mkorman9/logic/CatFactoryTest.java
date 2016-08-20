package com.github.mkorman9.logic;

import com.github.mkorman9.logic.data.CatData;
import com.github.mkorman9.logic.testhelper.CatsPersistenceTestHelper;
import com.github.mkorman9.model.Cat;
import com.github.mkorman9.model.CatsGroup;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class CatFactoryTest extends CatsPersistenceTestHelper {
    private final long groupId = 1L;
    private final String groupName = "Pirates";

    private List<CatsGroup> testGroups;

    private CatFactory catFactory;

    @Before
    public void setUp() throws Exception {
        testGroups = createTestGroups();
        super.setUp(testGroups, createTestCats());
        catFactory = new CatFactory(catsGroupRepository);
    }

    @Test
    public void shouldCreateEntityFromData() throws Exception {
        // given
        String roleName = "Pirate";
        String name = "Barnaba";
        int duelsWon = 12;
        CatData catData = createCatDataMock(groupId, roleName, name, duelsWon);

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
        Cat entity = createCat(1L, roleName, name, duelsWon, testGroups.get(0));

        // when
        CatData data = catFactory.createData(entity);

        // then
        assertThat(data.getRoleName()).isEqualTo(roleName);
        assertThat(data.getName()).isEqualTo(name);
        assertThat(data.getDuelsWon()).isEqualTo(duelsWon);
        assertThat(data.getGroup().getId()).isEqualTo(groupId);
        assertThat(data.getGroup().getName()).isEqualTo(groupName);
    }

    private List<CatsGroup> createTestGroups() {
        return Lists.newArrayList(createCatsGroup(groupId, groupName));
    }

    private List<Cat> createTestCats() {
        return Lists.newArrayList();
    }
}
