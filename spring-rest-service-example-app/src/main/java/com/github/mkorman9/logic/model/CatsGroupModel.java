package com.github.mkorman9.logic.model;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class CatsGroupModel {
    @NotNull
    private Long id;

    private String name;
}
