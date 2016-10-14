package com.github.mkorman9.logic.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CatsGroupDtoImpl implements CatsGroupDto {
    private Long id;
    private String name;
}
