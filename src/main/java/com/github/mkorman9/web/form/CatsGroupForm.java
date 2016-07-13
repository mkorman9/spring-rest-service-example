package com.github.mkorman9.web.form;

import com.github.mkorman9.logic.data.CatsGroupData;

import javax.validation.constraints.NotNull;

public class CatsGroupForm implements CatsGroupData {
    @NotNull
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
