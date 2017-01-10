package com.github.mkorman9.logging;

import com.github.mkorman9.logic.model.CatModel;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class CatsAuditAspect {
    @After("execution(* com.github.mkorman9.logic.CatLogic.addNewCat(..)) && args(catModel,..)")
    public void logCatAdded(CatModel catModel) {
        log.info("Added new cat " + catModel.getName());
    }

    @After("execution(* com.github.mkorman9.logic.CatLogic.updateCat(..)) && args(id,..)")
    public void logCatUpdated(Long id) {
        log.info("Updated cat with id " + id);
    }

    @After("execution(* com.github.mkorman9.logic.CatLogic.removeCat(..)) && args(id,..)")
    public void logCatRemoved(Long id) {
        log.info("Removed cat with id " + id);
    }
}
