package com.github.mkorman9.web.form;

import com.github.mkorman9.logic.data.CatsGroupDto;

import javax.validation.constraints.NotNull;

public class CatsGroupForm implements CatsGroupDto {
    @NotNull
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
