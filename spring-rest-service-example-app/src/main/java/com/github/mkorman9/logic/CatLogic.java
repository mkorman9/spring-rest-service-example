package com.github.mkorman9.logic;

import com.github.mkorman9.dao.CatRepository;
import com.github.mkorman9.entity.Cat;
import com.github.mkorman9.logic.model.CatModel;
import com.github.mkorman9.logic.exception.InvalidInputDataException;
import com.google.common.collect.ImmutableSet;
import javaslang.control.Try;
import lombok.val;
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
    public Set<CatModel> findAllCats() {
        return ImmutableSet.copyOf(catRepository.findAll())
                .stream()
                .map(cat -> catFactory.createModel(cat))
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public CatModel findSingleCat(Long id) throws InvalidInputDataException {
        return catFactory.createModel(findCat(id));
    }

    @Transactional
    public void addNewCat(CatModel catModel) {
        catRepository.save(catFactory.createEntity(catModel));
    }

    @Transactional
    public void removeCat(Long id) throws InvalidInputDataException {
        Try.run(() -> catRepository.delete(id))
                .onFailure(exc -> { throw new InvalidInputDataException("Cat with id " + id + " was not found"); });
    }

    @Transactional
    public void updateCat(Long id, CatModel catModel) throws InvalidInputDataException {
        catFactory.editEntity(findCat(id), catModel);
    }

    private Cat findCat(Long id) {
        val entity = catRepository.findOne(id);
        if (entity == null) {
            throw new InvalidInputDataException("Cat with id " + id + " was not found");
        }
        return entity;
    }
}
