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

    @NotNull(message="NotNull")
    @Size(min = 3, max = 50, message="Size")
    private String roleName;

    @NotNull(message="NotNull")
    @Size(min = 3, max = 50, message="Size")
    private String name;

    @NotNull(message="NotNull")
    @Min(value = 0, message="Min")
    private int duelsWon;

    @NotNull(message="NotNull")
    @Valid
    private CatsGroupModel group;
}
