package com.github.mkorman9.logic;

import com.github.mkorman9.dao.CatRepository;
import com.github.mkorman9.model.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CatLogic {
    @Autowired
    private CatRepository catRepository;

    @Transactional(readOnly = true)
    public Iterable<Cat> findAllCats() {
        return catRepository.findAll();
    }
}
