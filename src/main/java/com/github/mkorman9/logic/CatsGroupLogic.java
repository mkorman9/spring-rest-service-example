package com.github.mkorman9.logic;

import com.github.mkorman9.dao.CatsGroupRepository;
import com.github.mkorman9.model.CatsGroup;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatsGroupLogic {
    private CatsGroupRepository catsGroupRepository;

    @Autowired
    public CatsGroupLogic(CatsGroupRepository catsGroupRepository) {
        this.catsGroupRepository = catsGroupRepository;
    }

    public List<CatsGroup> findAll() {
        return Lists.newArrayList(catsGroupRepository.findAll());
    }
}
