package com.github.mkorman9.web.controller;

import com.github.mkorman9.web.form.ResponseForm;
import com.google.common.collect.Lists;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice(basePackages = "com.github.mkorman9.web.controller")
class ControllersCommons {
    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseForm("error", Lists.newArrayList(exception.getMessage())));
    }

    protected ResponseForm handlerBindingError(BindingResult bindingResult) {
        return new ResponseForm("error", bindingResult.getFieldErrors().stream()
                .map(error -> String.format("'%s' %s", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList()));
    }
}
