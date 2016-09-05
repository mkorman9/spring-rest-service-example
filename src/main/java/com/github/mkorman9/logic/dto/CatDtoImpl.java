package com.github.mkorman9.logic.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CatDtoImpl implements CatDto {
    private Long id;
    private String roleName;
    private String name;
    private Integer duelsWon;
    private CatsGroupDto group;
}
