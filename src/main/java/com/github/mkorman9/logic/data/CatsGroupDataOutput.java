package com.github.mkorman9.logic.data;

public class CatsGroupDataOutput implements CatsGroupData {
    private Long id;
    private String name;

    public CatsGroupDataOutput(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
