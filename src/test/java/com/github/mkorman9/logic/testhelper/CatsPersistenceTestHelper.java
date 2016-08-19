package com.github.mkorman9.logic.testhelper;

import com.github.mkorman9.dao.CatRepository;
import com.github.mkorman9.dao.CatsGroupRepository;
import com.github.mkorman9.model.Cat;
import com.github.mkorman9.model.CatsGroup;
import com.google.common.collect.Sets;
import org.mockito.stubbing.Answer;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class CatsPersistenceTestHelper extends CatsEntitiesFactoryTestHelper {
    protected CatsGroupRepository catsGroupRepository;
    protected CatRepository catRepository;

    private Set<CatsGroup> persistedGroups;
    private Set<Cat> persistedCats;

    protected void setUp(Collection<CatsGroup> persistedGroups, Collection<Cat> persistedCats) throws Exception {
        this.persistedGroups = Sets.newHashSet(persistedGroups);
        this.persistedCats = Sets.newHashSet(persistedCats);

        catsGroupRepository = mock(CatsGroupRepository.class);
        catRepository = mock(CatRepository.class);

        when(catsGroupRepository.findAll()).thenReturn(this.persistedGroups);
        when(catsGroupRepository.findOne(any(Long.class))).thenAnswer(findOneGroup());
        when(catRepository.findAll()).thenReturn(this.persistedCats);
        when(catRepository.save(any(Cat.class))).thenAnswer(addCat());
        when(catRepository.findOne(any(Long.class))).thenAnswer(findOneCat());
        doAnswer(deleteCat()).when(catRepository).delete(any(Long.class));
    }

    private Answer<CatsGroup> findOneGroup() {
        return invocationOnMock -> {
            Long id = (Long) invocationOnMock.getArguments()[0];
            return persistedGroups.stream()
                    .filter(g -> Objects.equals(g.getId(), id))
                    .findFirst()
                    .orElse(null);
        };
    }

    private Answer<Void> addCat() {
        return invocationOnMock -> {
            Cat newCat = (Cat) invocationOnMock.getArguments()[0];
            persistedCats.add(newCat);
            return null;
        };
    }

    private Answer<Cat> findOneCat() {
        return invocationOnMock -> {
            Long id = (Long) invocationOnMock.getArguments()[0];
            return persistedCats.stream()
                    .filter(c -> Objects.equals(c.getId(), id))
                    .findFirst()
                    .orElse(null);
        };
    }

    private Answer deleteCat() {
        return invocationOnMock -> {
            Long id = (Long) invocationOnMock.getArguments()[0];
            persistedCats.stream()
                    .filter(cat -> Objects.equals(cat.getId(), id))
                    .findFirst()
                    .ifPresent(cat -> persistedCats.remove(cat));
            return null;
        };
    }
}
