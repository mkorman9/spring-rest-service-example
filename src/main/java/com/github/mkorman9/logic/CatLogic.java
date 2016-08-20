package com.github.mkorman9.logic;

import com.github.mkorman9.dao.CatRepository;
import com.github.mkorman9.logic.data.CatData;
import com.github.mkorman9.model.Cat;
import com.google.common.collect.ImmutableSet;
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
    public Set<CatData> findAllCats() {
        return ImmutableSet.copyOf(catRepository.findAll())
                .stream()
                .map(cat -> catFactory.createData(cat))
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public CatData findSingleCat(Long id) {
        Cat entity = catRepository.findOne(id);
        if (entity == null) {
            throw new IllegalArgumentException("Entity with id " + id + " not found");
        }
        return catFactory.createData(entity);
    }

    @Transactional
    public void addNewCat(CatData catData) {
        Cat entity = catFactory.createEntity(catData);
        catRepository.save(entity);
    }

    @Transactional
    public void removeCat(Long id) {
        catRepository.delete(id);
    }

    @Transactional
    public void updateCat(Long id, CatData catData) {
        Cat entity = catRepository.findOne(id);
        if (entity == null) {
            throw new IllegalArgumentException("Entity with id " + id + " not found");
        }

        catFactory.editEntity(entity, catData);
    }
}
