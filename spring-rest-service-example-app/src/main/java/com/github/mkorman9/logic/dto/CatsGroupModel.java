package com.github.mkorman9.logic.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CatsGroupModel implements CatsGroupDto {
    private Long id;
    private String name;
}
