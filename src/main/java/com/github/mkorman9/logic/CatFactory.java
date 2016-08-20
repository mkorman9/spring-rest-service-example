package com.github.mkorman9.logic;

import com.github.mkorman9.dao.CatsGroupRepository;
import com.github.mkorman9.logic.data.CatData;
import com.github.mkorman9.logic.data.CatDataOutput;
import com.github.mkorman9.logic.data.CatsGroupData;
import com.github.mkorman9.logic.data.CatsGroupDataOutput;
import com.github.mkorman9.model.Cat;
import com.github.mkorman9.model.CatsGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CatFactory {
    private CatsGroupRepository catsGroupRepository;

    @Autowired
    public CatFactory(CatsGroupRepository catsGroupRepository) {
        this.catsGroupRepository = catsGroupRepository;
    }

    public Cat createEntity(CatData catData) {
        Cat cat = new Cat();
        editEntity(cat, catData);
        return cat;
    }

    public void editEntity(Cat entity, CatData catData) {
        entity.setRoleName(catData.getRoleName());
        entity.setName(catData.getName());
        entity.setDuelsWon(catData.getDuelsWon());
        entity.setGroup(convertDataGroupToEntity(catData.getGroup()));
    }

    public CatData createData(Cat entity) {
        return new CatDataOutput(entity.getId(),
                entity.getRoleName(),
                entity.getName(),
                entity.getDuelsWon(),
                convertEntityGroupToData(entity.getGroup()));
    }

    public CatsGroupData convertEntityGroupToData(CatsGroup group) {
        return new CatsGroupDataOutput(group.getId(), group.getName());
    }

    private CatsGroup convertDataGroupToEntity(CatsGroupData group) {
        return catsGroupRepository.findOne(group.getId());
    }
}
