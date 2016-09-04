package com.github.mkorman9.web.form.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseForm {
    private ResponseStatus status;
    private List<ResponseError> errors;
    private Object data;

    ResponseForm(ResponseStatus status, Object data, List<ResponseError> errors) {
        this.status = status;
        this.data = data;
        this.errors = errors;
    }

    public String getStatus() {
        return status.getStatus();
    }

    public List<ResponseError> getErrors() {
        return errors;
    }

    public Object getData() {
        return data;
    }

    public static Builder build(ResponseStatus status) {
        return new Builder(status);
    }

    public static class Builder {
        private ResponseStatus status;
        private Object data;
        private List<ResponseError> errors;

        Builder(ResponseStatus status) {
            this.status = status;
        }

        public Builder withData(Object data) {
            this.data = data;
            return this;
        }

        public Builder withError(ResponseError error) {
            this.errors = Lists.newArrayList(error);
            return this;
        }

        public Builder withErrors(List<ResponseError> error) {
            this.errors = ImmutableList.copyOf(error);
            return this;
        }

        public ResponseForm get() {
            return new ResponseForm(status, data, errors);
        }
    }
}
