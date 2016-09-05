package com.github.mkorman9.web.form;

import com.github.mkorman9.logic.dto.CatsGroupDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CatsGroupForm implements CatsGroupDto {
    @NotNull
    private Long id;

    private String name;
}
