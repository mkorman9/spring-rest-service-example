package com.github.mkorman9.logic;

import com.github.mkorman9.model.Cat;
import org.springframework.stereotype.Component;

@Component
public class CatFactory {
    public Cat createEntity(CatData catData) {
        Cat cat = new Cat();
        editEntity(cat, catData);
        return cat;
    }

    public void editEntity(Cat entity, CatData catData) {
        entity.setRoleName(catData.getRoleName());
        entity.setName(catData.getName());
        entity.setDuelsWon(catData.getDuelsWon());
    }
}
