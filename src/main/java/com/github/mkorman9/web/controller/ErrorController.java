package com.github.mkorman9.web.controller;

import com.github.mkorman9.web.form.ResponseForm;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
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
    public ResponseEntity error(HttpServletRequest request){
        Map<String, Object> body = getErrorAttributes(request);
        return ResponseEntity
                .status(HttpStatus.valueOf(Integer.parseInt(body.get("status").toString())))
                .body(new ResponseForm("error", Lists.newArrayList(body.get("status") + " " + body.get("error"))));
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest aRequest) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(aRequest);
        return errorAttributes.getErrorAttributes(requestAttributes, false);
    }
}