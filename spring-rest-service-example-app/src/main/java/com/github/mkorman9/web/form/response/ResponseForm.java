package com.github.mkorman9.web.form.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class ResponseForm {
    @Getter(AccessLevel.NONE) private ResponseStatus status;
    private Object error;
    private String timestamp;
    private Object data;

    public String getStatus() {
        return status.toString();
    }

    public static class ResponseFormBuilder {
        private String timestamp = DateTimeFormatter.ofPattern("YYYY-MM-dd'T'HH:mm:ssZ").format(ZonedDateTime.now());
    }
}
