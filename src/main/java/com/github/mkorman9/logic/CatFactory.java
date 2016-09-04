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
        return CatDataOutput.build()
                .withId(entity.getId())
                .withRoleName(entity.getRoleName())
                .withName(entity.getName())
                .withDuelsWon(entity.getDuelsWon())
                .withGroup(convertEntityGroupToData(entity.getGroup()))
                .get();
    }

    public CatsGroupData convertEntityGroupToData(CatsGroup group) {
        return CatsGroupDataOutput.build()
                .withId(group.getId())
                .withName(group.getName())
                .get();
    }

    private CatsGroup convertDataGroupToEntity(CatsGroupData group) {
        return catsGroupRepository.findOne(group.getId());
    }
}
