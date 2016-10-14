package com.github.mkorman9.web.form;

import com.github.mkorman9.logic.dto.CatDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
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
}
