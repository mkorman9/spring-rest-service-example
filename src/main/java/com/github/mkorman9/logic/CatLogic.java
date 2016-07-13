package com.github.mkorman9.logic;

import com.github.mkorman9.dao.CatRepository;
import com.github.mkorman9.logic.data.CatData;
import com.github.mkorman9.model.Cat;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CatLogic {
    private static Logger LOGGER = LoggerFactory.getLogger(CatLogic.class);

    private CatRepository catRepository;
    private CatFactory catFactory;

    @Autowired
    public CatLogic(CatRepository catRepository, CatFactory catFactory) {
        this.catRepository = catRepository;
        this.catFactory = catFactory;
    }

    public List<Cat> findAllCats() {
        return Lists.newArrayList(catRepository.findAll());
    }

    public Cat findSingleCat(Long id) {
        Cat entity = catRepository.findOne(id);
        if (entity == null) {
            throw new IllegalArgumentException("Entity with id " + id + " not found");
        }
        return entity;
    }

    @Transactional
    public void addNewCat(CatData catData) {
        Cat entity = catFactory.createEntity(catData);
        catRepository.save(entity);
        LOGGER.info("Added new cat " + entity.toString());
    }

    @Transactional
    public void removeCat(Long id) {
        catRepository.delete(id);
        LOGGER.info("Removed cat with id " + id);
    }

    @Transactional
    public void updateCat(Long id, CatData catData) {
        Cat entity = catRepository.findOne(id);
        if (entity == null) {
            throw new IllegalArgumentException("Entity with id " + id + " not found");
        }

        catFactory.editEntity(entity, catData);
        LOGGER.info("Updated cat with id " + id + " with " + entity.toString());
    }
}
