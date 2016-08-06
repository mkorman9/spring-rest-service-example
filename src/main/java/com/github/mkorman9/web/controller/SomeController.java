package com.github.mkorman9.web.controller;

import com.github.mkorman9.logic.SomeCachableLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
public class SomeController {
    private SomeCachableLogic someCachableLogic;

    @Autowired
    public SomeController(SomeCachableLogic someCachableLogic) {
        this.someCachableLogic = someCachableLogic;
    }

    @RequestMapping("/")
    public String testCache() {
        return someCachableLogic.someMethod();
    }
}
