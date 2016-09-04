package com.github.mkorman9.web.form.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseError {
    private String field;
    private String message;

    ResponseError(String message, String field) {
        this.message = message;
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    public static Builder build(String message) {
        return new Builder(message);
    }

    public static class Builder {
        private String message;
        private String field;

        Builder(String message) {
            this.message = message;
        }

        public Builder refersToField(String field) {
            this.field = field;
            return this;
        }

        public ResponseError get() {
            return new ResponseError(message, field);
        }
    }
}
