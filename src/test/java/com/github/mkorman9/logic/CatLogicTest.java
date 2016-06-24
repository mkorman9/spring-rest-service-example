package com.github.mkorman9.logic;

import com.github.mkorman9.dao.CatRepository;
import com.github.mkorman9.model.Cat;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CatLogicTest {
    @Mock
    private CatRepository catRepository;

    @InjectMocks
    private CatLogic catLogic;

    private static final List<Cat> CATS = Lists.newArrayList(
            new Cat("Lumberjack", "John", 12),
            new Cat("Pirate", "Paul", 13)
    );

    @Before
    public void setUp() throws Exception {
        when(catRepository.findAll()).thenReturn(CATS);
    }

    @Test
    public void shouldListAllCats() throws Exception {
        List<Cat> catsFound = catLogic.findAllCats();

        assertThat(catsFound).isEqualTo(CATS);
    }
}
