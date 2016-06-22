package com.github.mkorman9.web.controller;

import com.github.mkorman9.logic.CatLogic;
import com.github.mkorman9.model.Cat;
import com.github.mkorman9.web.form.CatForm;
import com.github.mkorman9.web.form.ResponseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public ResponseForm addNewCat(@RequestBody @Valid CatForm catForm) {
        catLogic.addNewCat(catForm);
        return new ResponseForm("ok");
    }
}
