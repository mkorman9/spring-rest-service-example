package com.github.mkorman9.web.form.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class ResponseForm {
    @Getter(AccessLevel.NONE) private ResponseStatus status;
    private List<ResponseError> errors;
    private Object data;

    public String getStatus() {
        return status.toString();
    }
}
