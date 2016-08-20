package com.github.mkorman9.logic.data;

public class CatDataOutput implements CatData {
    private Long id;
    private String roleName;
    private String name;
    private Integer duelsWon;
    private CatsGroupData group;

    public CatDataOutput(Long id, String roleName, String name, Integer duelsWon, CatsGroupData group) {
        this.id = id;
        this.roleName = roleName;
        this.name = name;
        this.duelsWon = duelsWon;
        this.group = group;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getRoleName() {
        return roleName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getDuelsWon() {
        return duelsWon;
    }

    @Override
    public CatsGroupData getGroup() {
        return group;
    }
}
