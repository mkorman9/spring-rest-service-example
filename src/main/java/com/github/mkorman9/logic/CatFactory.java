package com.github.mkorman9.logic;

import com.github.mkorman9.dao.CatsGroupRepository;
import com.github.mkorman9.logic.dto.CatDto;
import com.github.mkorman9.logic.dto.CatDtoImpl;
import com.github.mkorman9.logic.dto.CatsGroupDto;
import com.github.mkorman9.logic.dto.CatsGroupDtoImpl;
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

    public Cat createEntity(CatDto catDto) {
        Cat cat = new Cat();
        editEntity(cat, catDto);
        return cat;
    }

    public void editEntity(Cat entity, CatDto catDto) {
        entity.setRoleName(catDto.getRoleName());
        entity.setName(catDto.getName());
        entity.setDuelsWon(catDto.getDuelsWon());
        entity.setGroup(convertDataGroupToEntity(catDto.getGroup()));
    }

    public CatDto createData(Cat entity) {
        return CatDtoImpl.build()
                .withId(entity.getId())
                .withRoleName(entity.getRoleName())
                .withName(entity.getName())
                .withDuelsWon(entity.getDuelsWon())
                .withGroup(convertEntityGroupToData(entity.getGroup()))
                .get();
    }

    public CatsGroupDto convertEntityGroupToData(CatsGroup group) {
        return CatsGroupDtoImpl.build()
                .withId(group.getId())
                .withName(group.getName())
                .get();
    }

    private CatsGroup convertDataGroupToEntity(CatsGroupDto group) {
        CatsGroup groupEntity = catsGroupRepository.findOne(group.getId());
        if (groupEntity == null) {
            throw new IllegalStateException("Group with id " + group.getId() + " was not found");
        }
        return groupEntity;
    }
}
