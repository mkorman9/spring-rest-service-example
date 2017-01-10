package com.github.mkorman9.logic;

import com.github.mkorman9.dao.CatsGroupRepository;
import com.github.mkorman9.entity.Cat;
import com.github.mkorman9.entity.CatsGroup;
import com.github.mkorman9.logic.model.CatModel;
import com.github.mkorman9.logic.model.CatsGroupModel;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CatFactory {
    private CatsGroupRepository catsGroupRepository;

    @Autowired
    public CatFactory(CatsGroupRepository catsGroupRepository) {
        this.catsGroupRepository = catsGroupRepository;
    }

    public Cat createEntity(CatModel catModel) {
        Cat cat = new Cat();
        editEntity(cat, catModel);
        return cat;
    }

    public void editEntity(Cat entity, CatModel catModel) {
        entity.setRoleName(catModel.getRoleName());
        entity.setName(catModel.getName());
        entity.setDuelsWon(catModel.getDuelsWon());
        entity.setGroup(convertGroupModelToEntity(catModel.getGroup()));
    }

    public CatModel createModel(Cat entity) {
        return CatModel.builder()
                .id(entity.getId())
                .roleName(entity.getRoleName())
                .name(entity.getName())
                .duelsWon(entity.getDuelsWon())
                .group(convertGroupEntityToModel(entity.getGroup()))
                .build();
    }

    public CatsGroupModel convertGroupEntityToModel(CatsGroup group) {
        return CatsGroupModel.builder()
                .id(group.getId())
                .name(group.getName())
                .build();
    }

    private CatsGroup convertGroupModelToEntity(CatsGroupModel group) {
        val groupEntity = catsGroupRepository.findOne(group.getId());
        if (groupEntity == null) {
            throw new IllegalStateException("Group with id " + group.getId() + " was not found");
        }
        return groupEntity;
    }
}
