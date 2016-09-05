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
        return CatDtoImpl.builder()
                .id(entity.getId())
                .roleName(entity.getRoleName())
                .name(entity.getName())
                .duelsWon(entity.getDuelsWon())
                .group(convertEntityGroupToData(entity.getGroup()))
                .build();
    }

    public CatsGroupDto convertEntityGroupToData(CatsGroup group) {
        return CatsGroupDtoImpl.builder()
                .id(group.getId())
                .name(group.getName())
                .build();
    }

    private CatsGroup convertDataGroupToEntity(CatsGroupDto group) {
        CatsGroup groupEntity = catsGroupRepository.findOne(group.getId());
        if (groupEntity == null) {
            throw new IllegalStateException("Group with id " + group.getId() + " was not found");
        }
        return groupEntity;
    }
}
