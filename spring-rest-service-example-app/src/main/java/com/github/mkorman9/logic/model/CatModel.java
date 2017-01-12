package com.github.mkorman9.logic.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatModel {
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
    private CatsGroupModel group;
}