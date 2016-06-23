package com.github.mkorman9.web.controller;

import com.github.mkorman9.logic.CatLogic;
import com.github.mkorman9.model.Cat;
import com.github.mkorman9.web.form.CatForm;
import com.github.mkorman9.web.form.ResponseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class FrontController {
    @Autowired
    private CatLogic catLogic;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Cat> findAllCats() {
        return catLogic.findAllCats();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseForm addNewCat(@RequestBody @Valid CatForm catForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return handlerBindingError(bindingResult);
        }

        catLogic.addNewCat(catForm);
        return new ResponseForm("ok");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseForm deleteCat(@PathVariable("id") Long id) {
        catLogic.removeCat(id);
        return new ResponseForm("ok");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler() {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseForm("error"));
    }

    private ResponseForm handlerBindingError(BindingResult bindingResult) {
        return new ResponseForm("error", bindingResult.getFieldErrors().stream()
                        .map(error -> String.format("'%s' %s", error.getField(), error.getDefaultMessage()))
                        .collect(Collectors.toList()));
    }
}
