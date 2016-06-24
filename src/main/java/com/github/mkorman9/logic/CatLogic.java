package com.github.mkorman9.logic;

import com.github.mkorman9.dao.CatRepository;
import com.github.mkorman9.model.Cat;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CatLogic {
    @Autowired
    private CatRepository catRepository;
    @Autowired
    private CatFactory catFactory;

    @Transactional(readOnly = true)
    public List<Cat> findAllCats() {
        return Lists.newArrayList(catRepository.findAll());
    }

    @Transactional
    public void addNewCat(CatData catData) {
        catRepository.save(catFactory.createEntity(catData));
    }

    @Transactional
    public void removeCat(Long id) {
        catRepository.delete(id);
    }

    @Transactional
    public void updateCat(Long id, CatData catData) {
        Cat entity = catRepository.findOne(id);
        catFactory.editEntity(entity, catData);
    }
}
