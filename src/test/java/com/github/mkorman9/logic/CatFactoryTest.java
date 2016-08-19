package com.github.mkorman9.logic;

import com.github.mkorman9.logic.data.CatData;
import com.github.mkorman9.logic.testhelper.CatsPersistenceTestHelper;
import com.github.mkorman9.model.Cat;
import com.github.mkorman9.model.CatsGroup;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.fest.assertions.Assertions.assertThat;

public class CatFactoryTest extends CatsPersistenceTestHelper {
    private final long groupId = 1L;
    private final String groupName = "Pirates";

    private CatFactory catFactory;

    @Before
    public void setUp() throws Exception {
        super.setUp(createTestGroups(), createTestCats());
        catFactory = new CatFactory(catsGroupRepository);
    }

    @Test
    public void shouldCreateEntityFromData() throws Exception {
        String roleName = "Pirate";
        String name = "Barnaba";
        int duelsWon = 12;
        CatData catData = createCatDataMock(groupId, roleName, name, duelsWon);

        Cat cat = catFactory.createEntity(catData);

        assertThat(cat.getRoleName()).isEqualTo(roleName);
        assertThat(cat.getName()).isEqualTo(name);
        assertThat(cat.getDuelsWon()).isEqualTo(duelsWon);
        assertThat(cat.getGroup().getId()).isEqualTo(groupId);
        assertThat(cat.getGroup().getName()).isEqualTo(groupName);
    }

    private Set<CatsGroup> createTestGroups() {
        return Sets.newHashSet(createCatsGroup(groupId, groupName));
    }

    private Set<Cat> createTestCats() {
        return Sets.newHashSet();
    }
}
