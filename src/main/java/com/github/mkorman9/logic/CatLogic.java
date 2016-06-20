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

    @Transactional(readOnly = true)
    public List<Cat> findAllCats() {
        return Lists.newArrayList(catRepository.findAll());
    }
}
