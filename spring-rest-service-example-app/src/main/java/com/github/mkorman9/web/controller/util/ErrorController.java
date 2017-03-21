package com.github.mkorman9.web.controller.util;

import com.github.mkorman9.web.form.response.ResponseError;
import com.github.mkorman9.web.form.response.ResponseForm;
import com.github.mkorman9.web.form.response.ResponseStatus;
import com.google.common.collect.ImmutableList;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/error")
public class ErrorController implements org.springframework.boot.autoconfigure.web.ErrorController {
    private final ErrorAttributes errorAttributes;

    @Autowired
    public ErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping
    public ResponseEntity error(HttpServletRequest request) {
        val body = getErrorAttributes(request);
        return ResponseEntity
                .status(HttpStatus.valueOf(Integer.parseInt(body.get("status").toString())))
                .body(ResponseForm.builder()
                        .status(ResponseStatus.ERROR)
                        .error(ResponseError.builder()
                                .message(body.get("status") + " " + body.get("error"))
                                .build()
                        )
                        .build());
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest aRequest) {
        val requestAttributes = new ServletRequestAttributes(aRequest);
        return errorAttributes.getErrorAttributes(requestAttributes, false);
    }
}
