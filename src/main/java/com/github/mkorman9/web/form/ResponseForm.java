package com.github.mkorman9.web.form;

import com.google.common.collect.Lists;

import java.util.List;

public class ResponseForm {
    private String status;
    private List<Error> errors;
    private Object data;

    public ResponseForm(String status) {
        this(status, Lists.newArrayList());
    }

    public ResponseForm(String status, List<Error> errors) {
        this.status = status;
        this.errors = errors;
    }

    public ResponseForm(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static class Error {
        private String field;
        private String message;

        public Error(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
