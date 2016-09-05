package com.github.mkorman9.logic.data;

public interface CatDto {
    Long getId();

    String getRoleName();

    String getName();

    Integer getDuelsWon();

    CatsGroupDto getGroup();
}
