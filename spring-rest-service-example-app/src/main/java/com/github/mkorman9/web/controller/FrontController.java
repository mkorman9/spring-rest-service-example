package com.github.mkorman9.web.controller;

import com.github.mkorman9.logic.CatLogic;
import com.github.mkorman9.logic.CatsGroupLogic;
import com.github.mkorman9.logic.model.CatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class FrontController {
    private CatLogic catLogic;
    private CatsGroupLogic catsGroupLogic;

    @Autowired
    public FrontController(CatLogic catLogic, CatsGroupLogic catsGroupLogic) {
        this.catLogic = catLogic;
        this.catsGroupLogic = catsGroupLogic;
    }

    @RequestMapping(value = "/cats/all", method = RequestMethod.GET)
    public Object findAllCats() {
        return catLogic.findAllCats();
    }

    @RequestMapping(value = "/cats/get/{id}", method = RequestMethod.GET)
    public Object getSingleCat(@PathVariable("id") Long id) {
        return catLogic.findSingleCat(id);
    }

    @RequestMapping(value = "/cats/add", method = RequestMethod.POST)
    public Object addNewCat(@RequestBody CatModel catForm) {
        catLogic.addNewCat(catForm);
        return "ok";
    }

    @RequestMapping(value = "/cats/delete/{id}", method = RequestMethod.DELETE)
    public Object deleteCat(@PathVariable("id") Long id) {
        catLogic.removeCat(id);
        return "ok";
    }

    @RequestMapping(value = "/cats/edit/{id}", method = RequestMethod.PUT)
    public Object editCat(@PathVariable("id") Long id, @RequestBody CatModel catForm) {
        catLogic.updateCat(id, catForm);
        return "ok";
    }

    @RequestMapping(value = "/groups/all", method = RequestMethod.GET)
    public Object findAllGroups() {
        return catsGroupLogic.findAll();
    }
}
