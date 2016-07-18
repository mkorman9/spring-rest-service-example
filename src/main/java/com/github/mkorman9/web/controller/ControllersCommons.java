package com.github.mkorman9.web.controller;

import com.github.mkorman9.web.form.ResponseForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice(basePackages = "com.github.mkorman9.web.controller")
class ControllersCommons {
    private static Logger LOGGER = LoggerFactory.getLogger(ControllersCommons.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(Exception exception) {
        LOGGER.error("Error during request processing", exception);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseForm("error", new ResponseForm.Error(exception.getMessage())));
    }

    protected ResponseForm handleBindingError(BindingResult bindingResult) {
        return new ResponseForm("error", bindingResult.getFieldErrors().stream()
                .map(error -> new ResponseForm.Error(error.getField(), error.getCode()))
                .collect(Collectors.toList()));
    }
}
