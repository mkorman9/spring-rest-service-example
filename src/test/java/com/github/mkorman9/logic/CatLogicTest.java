package com.github.mkorman9.logic;

import com.github.mkorman9.dao.CatRepository;
import com.github.mkorman9.logic.data.CatDto;
import com.github.mkorman9.model.Cat;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.github.mkorman9.logic.CatsPersistenceTestHelper.createCat;
import static com.github.mkorman9.logic.CatsPersistenceTestHelper.createCatDtoMock;
import static com.github.mkorman9.logic.CatsPersistenceTestHelper.createCatsGroup;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CatLogicTest {
    @Mock
    private CatRepository catRepository;
    @Mock
    private CatFactory catFactory;
    @InjectMocks
    private CatLogic catLogic;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldConvertAndPersistNewCat() throws Exception {
        // given
        CatDto catToAdd = createCatDtoMock("Pirate", "Barnaba", 13, 1L);
        Cat catConverted = createCat(1L, "Pirate", "Barnaba", 13, createCatsGroup(0L, "Pirates"));
        when(catFactory.createEntity(eq(catToAdd))).thenReturn(catConverted);

        // when
        catLogic.addNewCat(catToAdd);

        // then
        verify(catFactory).createEntity(eq(catToAdd));
        verify(catRepository).save(eq(catConverted));
    }

    @Test
    public void shouldEditExistingCat() throws Exception {
        // given
        Cat catToUpdate = createCat(0L, "Pirate", "Barnaba", 14, createCatsGroup(0L, "Pirates"));
        CatDto catDtoToUpdate = createCatDtoMock("Pirate", "Barnaba", 13, 0L);
        when(catRepository.findOne(eq(0L))).thenReturn(catToUpdate);

        // when
        catLogic.updateCat(0L, catDtoToUpdate);

        // then
        verify(catRepository).findOne(eq(0L));
        verify(catFactory).editEntity(eq(catToUpdate), eq(catDtoToUpdate));
    }

    @Test
    public void shouldThrowExceptionWhenTryingToEditNonExistingCat() throws Exception {
        // given
        CatDto catDtoToUpdate = createCatDtoMock("Pirate", "Barnaba", 13, 0L);
        when(catRepository.findOne(eq(0L))).thenReturn(null);

        expectedException.expect(IllegalArgumentException.class);

        // when
        catLogic.updateCat(1L, catDtoToUpdate);
    }

    @Test
    public void shouldDeleteCat() throws Exception {
        // when
        catLogic.removeCat(1L);

        // then
        verify(catRepository).delete(eq(1L));
    }

    @Test
    public void shouldThrowWhenSingleCatNotFound() throws Exception {
        // given
        when(catRepository.findOne(eq(1000L))).thenReturn(null);

        expectedException.expect(IllegalArgumentException.class);

        // then
        catLogic.findSingleCat(1000L);
    }
}
