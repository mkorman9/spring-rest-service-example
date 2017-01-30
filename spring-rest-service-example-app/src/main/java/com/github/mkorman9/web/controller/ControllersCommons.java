package com.github.mkorman9.web.controller;

import com.github.mkorman9.logic.exception.InvalidInputDataException;
import com.github.mkorman9.web.form.response.ResponseError;
import com.github.mkorman9.web.form.response.ResponseForm;
import com.github.mkorman9.web.form.response.ResponseStatus;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Optional;
import java.util.stream.Collectors;

import static javaslang.API.*;
import static javaslang.Predicates.instanceOf;

@ControllerAdvice(basePackages = "com.github.mkorman9.web.controller")
@Slf4j
class ControllersCommons {
    private static final String INTERNAL_ERROR_RESPONSE_TEXT = "Internal error while processing request";

    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(Exception exception) {
        decideOnLoggingException(exception).ifPresent(exc -> log.error("Error during request processing: ", exc));

        return ResponseEntity
                .status(resolveResponseStatus(exception))
                .body(ResponseForm.builder()
                        .status(ResponseStatus.ERROR)
                        .errors(ImmutableList.of(ResponseError.builder()
                                .message(resolveResponseText(exception))
                                .build()
                        ))
                        .build());
    }

    protected ResponseForm handleBindingError(BindingResult bindingResult) {
        return ResponseForm.builder()
                .status(ResponseStatus.ERROR)
                .errors(bindingResult.getFieldErrors().stream()
                        .map(error -> ResponseError.builder()
                                .message(error.getCode())
                                .field(error.getField())
                                .build()
                        )
                        .collect(Collectors.toList()))
                .build();
    }

    private HttpStatus resolveResponseStatus(Exception exception) {
        return Match(exception).of(
                Case(instanceOf(InvalidInputDataException.class), HttpStatus.BAD_REQUEST),
                Case(instanceOf(MethodArgumentTypeMismatchException.class), HttpStatus.BAD_REQUEST),
                Case($(), HttpStatus.INTERNAL_SERVER_ERROR)
        );
    }

    private String resolveResponseText(Exception exception) {
        return Match(exception).of(
                Case(instanceOf(InvalidInputDataException.class), exception.getMessage()),
                Case(instanceOf(MethodArgumentTypeMismatchException.class), exception.getMessage()),
                Case($(), INTERNAL_ERROR_RESPONSE_TEXT)
        );
    }

    private Optional<Exception> decideOnLoggingException(Exception exception) {
        return Match(exception).of(
                Case(instanceOf(InvalidInputDataException.class), Optional.empty()),
                Case(instanceOf(MethodArgumentTypeMismatchException.class), Optional.empty()),
                Case($(), Optional.of(exception))
        );
    }
}
