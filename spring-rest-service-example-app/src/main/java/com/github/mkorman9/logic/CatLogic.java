package com.github.mkorman9.logic;

import com.github.mkorman9.dao.CatRepository;
import com.github.mkorman9.entity.Cat;
import com.github.mkorman9.logic.dto.CatDto;
import com.github.mkorman9.logic.exception.InvalidInputDataException;
import com.google.common.collect.ImmutableSet;
import javaslang.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CatLogic {
    private CatRepository catRepository;
    private CatFactory catFactory;

    @Autowired
    public CatLogic(CatRepository catRepository, CatFactory catFactory) {
        this.catRepository = catRepository;
        this.catFactory = catFactory;
    }

    @Transactional(readOnly = true)
    public Set<CatDto> findAllCats() {
        return ImmutableSet.copyOf(catRepository.findAll())
                .stream()
                .map(cat -> catFactory.createDto(cat))
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public CatDto findSingleCat(Long id) throws InvalidInputDataException {
        return catFactory.createDto(findCat(id));
    }

    @Transactional
    public void addNewCat(CatDto catDto) {
        catRepository.save(catFactory.createEntity(catDto));
    }

    @Transactional
    public void removeCat(Long id) throws InvalidInputDataException {
        Try.run(() -> catRepository.delete(id))
                .onFailure(exc -> reportInputError("Cat with id " + id + " was not found"));
    }

    @Transactional
    public void updateCat(Long id, CatDto catDto) throws InvalidInputDataException {
        catFactory.editEntity(findCat(id), catDto);
    }

    private Cat findCat(Long id) {
        Cat entity = catRepository.findOne(id);
        if (entity == null) {
            throw new InvalidInputDataException("Cat with id " + id + " was not found");
        }
        return entity;
    }

    private void reportInputError(String message) {
        throw new InvalidInputDataException(message);
    }
}
