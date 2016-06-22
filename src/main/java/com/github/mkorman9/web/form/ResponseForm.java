package com.github.mkorman9.web.form;

public class ResponseForm {
    private String status;

    public ResponseForm(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
