package com.github.mkorman9.logic;

import com.github.mkorman9.dao.CatsGroupRepository;
import com.github.mkorman9.logic.model.CatsGroupModel;
import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CatsGroupLogic {
    private CatsGroupRepository catsGroupRepository;
    private CatFactory catFactory;

    @Autowired
    public CatsGroupLogic(CatsGroupRepository catsGroupRepository, CatFactory catFactory) {
        this.catsGroupRepository = catsGroupRepository;
        this.catFactory = catFactory;
    }

    @Transactional(readOnly = true)
    public Set<CatsGroupModel> findAll() {
        return ImmutableSet.copyOf(catsGroupRepository.findAll())
                .stream()
                .map(group -> catFactory.convertGroupEntityToModel(group))
                .collect(Collectors.toSet());
    }
}
