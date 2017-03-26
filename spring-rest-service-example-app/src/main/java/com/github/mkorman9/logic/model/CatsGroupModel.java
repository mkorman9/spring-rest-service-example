package com.github.mkorman9.logic.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatsGroupModel {
    @NotNull(message="NotNull")
    private long id;

    private String name;
}
