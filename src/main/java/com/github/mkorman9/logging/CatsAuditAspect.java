package com.github.mkorman9.logging;

import com.github.mkorman9.logic.CatLogic;
import com.github.mkorman9.logic.data.CatData;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CatsAuditAspect {
    private static Logger LOGGER = LoggerFactory.getLogger(CatLogic.class);

    @After("execution(* com.github.mkorman9.logic.CatLogic.addNewCat(..))")
    public void logCatAdded(JoinPoint joinPoint) {
        CatData catData = (CatData) joinPoint.getArgs()[0];
        LOGGER.info("Added new cat " + catData.getName());
    }

    @After("execution(* com.github.mkorman9.logic.CatLogic.updateCat(..))")
    public void logCatUpdated(JoinPoint joinPoint) {
        Long id = (Long) joinPoint.getArgs()[0];
        LOGGER.info("Updated cat with id " + id);
    }

    @After("execution(* com.github.mkorman9.logic.CatLogic.removeCat(..))")
    public void logCatRemoved(JoinPoint joinPoint) {
        Long id = (Long) joinPoint.getArgs()[0];
        LOGGER.info("Removed cat with id " + id);
    }
}
