package com.github.mkorman9.web;

import com.github.mkorman9.logic.CatLogic;
import com.github.mkorman9.model.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class FrontController {
    @Autowired
    private CatLogic catLogic;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Cat> findAllCats() {
        return catLogic.findAllCats();
    }
}
