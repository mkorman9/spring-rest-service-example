package com.github.mkorman9.logic.dto;

public interface CatDto {
    Long getId();

    String getRoleName();

    String getName();

    Integer getDuelsWon();

    CatsGroupDto getGroup();
}
