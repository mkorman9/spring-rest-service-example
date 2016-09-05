package com.github.mkorman9.web.form.response;

public enum ResponseStatus {
    OK("ok"),
    ERROR("error");

    private String status;

    ResponseStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
