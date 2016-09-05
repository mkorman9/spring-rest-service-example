package com.github.mkorman9.web.form;

import com.github.mkorman9.logic.data.CatDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CatForm implements CatDto {
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    private String roleName;

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    @Min(0)
    private Integer duelsWon;

    @NotNull
    @Valid
    private CatsGroupForm group;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDuelsWon() {
        return duelsWon;
    }

    public void setDuelsWon(Integer duelsWon) {
        this.duelsWon = duelsWon;
    }

    public CatsGroupForm getGroup() {
        return group;
    }

    public void setGroup(CatsGroupForm group) {
        this.group = group;
    }
}
