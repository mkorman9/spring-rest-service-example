package com.github.mkorman9.web.form;

import com.google.common.collect.Lists;

import java.util.List;

public class ResponseForm {
    private String status;
    private List<String> errors;
    private Object data;

    public ResponseForm(String status) {
        this(status, Lists.newArrayList());
    }

    public ResponseForm(String status, List<String> errors) {
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

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
