package com.github.mkorman9.web.form.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class ResponseForm {
    @Getter(AccessLevel.NONE) private ResponseStatus status;
    private List<ResponseError> errors;
    private String timestamp;
    private Object data;

    public String getStatus() {
        return status.toString();
    }

    public static class ResponseFormBuilder {
        private String timestamp = DateTimeFormat.forPattern("YYYY-MM-dd'T'HH:mm:ssZ").print(DateTime.now());
    }
}
