package com.github.mkorman9.logic;

import com.github.mkorman9.dao.CatRepository;
import com.github.mkorman9.entity.Cat;
import com.github.mkorman9.logic.model.CatModel;
import com.github.mkorman9.logic.exception.InvalidInputDataException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;

import static com.github.mkorman9.logic.CatsPersistenceTestHelper.*;
import static org.mockito.Matchers.eq;
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
        CatModel catToAdd = createCatModelMock("Pirate", "Barnaba", 13, 1L);
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
        CatModel catModelToUpdate = createCatModelMock("Pirate", "Barnaba", 13, 0L);
        when(catRepository.findOne(eq(0L))).thenReturn(catToUpdate);

        // when
        catLogic.updateCat(0L, catModelToUpdate);

        // then
        verify(catRepository).findOne(eq(0L));
        verify(catFactory).editEntity(eq(catToUpdate), eq(catModelToUpdate));
    }

    @Test
    public void shouldThrowExceptionWhenTryingToEditNonExistingCat() throws Exception {
        // given
        CatModel catModelToUpdate = createCatModelMock("Pirate", "Barnaba", 13, 0L);
        when(catRepository.findOne(eq(0L))).thenReturn(null);

        expectedException.expect(InvalidInputDataException.class);

        // when
        catLogic.updateCat(1L, catModelToUpdate);
    }

    @Test
    public void shouldDeleteCat() throws Exception {
        // when
        catLogic.removeCat(1L);

        // then
        verify(catRepository).delete(eq(1L));
    }

    @Test
    public void shouldThrowWhenTryingToDeleteNonExistingCat() throws Exception {
        // given
        Mockito.doThrow(new EmptyResultDataAccessException(1))
                .when(catRepository)
                .delete(eq(666L));

        expectedException.expect(InvalidInputDataException.class);

        // when
        catLogic.removeCat(666L);
    }

    @Test
    public void shouldThrowWhenSingleCatNotFound() throws Exception {
        // given
        when(catRepository.findOne(eq(1000L))).thenReturn(null);

        expectedException.expect(InvalidInputDataException.class);

        // when
        catLogic.findSingleCat(1000L);
    }
}
