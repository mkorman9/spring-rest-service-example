package com.github.mkorman9.logic.data;

public interface CatData {
    Long getId();

    String getRoleName();

    String getName();

    Integer getDuelsWon();

    CatsGroupData getGroup();
}
