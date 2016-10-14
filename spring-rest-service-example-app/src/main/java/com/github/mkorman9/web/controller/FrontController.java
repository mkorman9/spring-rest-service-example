package com.github.mkorman9.web.controller;

import com.github.mkorman9.logic.CatLogic;
import com.github.mkorman9.logic.CatsGroupLogic;
import com.github.mkorman9.web.form.CatForm;
import com.github.mkorman9.web.form.response.ResponseForm;
import com.github.mkorman9.web.form.response.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class FrontController extends ControllersCommons {
    private CatLogic catLogic;
    private CatsGroupLogic catsGroupLogic;

    @Autowired
    public FrontController(CatLogic catLogic, CatsGroupLogic catsGroupLogic) {
        this.catLogic = catLogic;
        this.catsGroupLogic = catsGroupLogic;
    }

    @RequestMapping(value = "/cats/all", method = RequestMethod.GET)
    public ResponseForm findAllCats() {
        return ResponseForm.builder()
                .status(ResponseStatus.OK)
                .data(catLogic.findAllCats())
                .build();
    }

    @RequestMapping(value = "/cats/get/{id}", method = RequestMethod.GET)
    public ResponseForm getSingleCat(@PathVariable("id") Long id) {
        return ResponseForm.builder()
                .status(ResponseStatus.OK)
                .data(catLogic.findSingleCat(id))
                .build();
    }

    @RequestMapping(value = "/cats/add", method = RequestMethod.POST)
    public ResponseForm addNewCat(@RequestBody @Valid CatForm catForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return handleBindingError(bindingResult);
        }

        catLogic.addNewCat(catForm);
        return ResponseForm.builder()
                .status(ResponseStatus.OK)
                .build();
    }

    @RequestMapping(value = "/cats/delete/{id}", method = RequestMethod.DELETE)
    public ResponseForm deleteCat(@PathVariable("id") Long id) {
        catLogic.removeCat(id);
        return ResponseForm.builder()
                .status(ResponseStatus.OK)
                .build();
    }

    @RequestMapping(value = "/cats/edit/{id}", method = RequestMethod.PUT)
    public ResponseForm editCat(@PathVariable("id") Long id, @RequestBody @Valid CatForm catForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return handleBindingError(bindingResult);
        }

        catLogic.updateCat(id, catForm);
        return ResponseForm.builder()
                .status(ResponseStatus.OK)
                .build();
    }

    @RequestMapping(value = "/groups/all", method = RequestMethod.GET)
    public ResponseForm findAllGroups() {
        return ResponseForm.builder()
                .status(ResponseStatus.OK)
                .data(catsGroupLogic.findAll())
                .build();
    }
}
