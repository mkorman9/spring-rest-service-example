package com.github.mkorman9.web.controller;

import com.github.mkorman9.logic.CatLogic;
import com.github.mkorman9.model.Cat;
import com.github.mkorman9.web.form.CatForm;
import com.github.mkorman9.web.form.ResponseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/")
public class FrontController extends ControllersCommons {
    @Autowired
    private CatLogic catLogic;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Cat> findAllCats() {
        return catLogic.findAllCats();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Cat getSingleCat(@PathVariable("id") Long id) {
        return catLogic.findSingleCat(id);
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

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
    public ResponseForm editCat(@PathVariable("id") Long id, @RequestBody @Valid CatForm catForm) {
        catLogic.updateCat(id, catForm);
        return new ResponseForm("ok");
    }
}
