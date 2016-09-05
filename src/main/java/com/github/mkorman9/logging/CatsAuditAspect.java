package com.github.mkorman9.logging;

import com.github.mkorman9.logic.CatLogic;
import com.github.mkorman9.logic.dto.CatDto;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CatsAuditAspect {
    private static Logger LOGGER = LoggerFactory.getLogger(CatLogic.class);

    @After("execution(* com.github.mkorman9.logic.CatLogic.addNewCat(..)) && args(catDto,..)")
    public void logCatAdded(CatDto catDto) {
        LOGGER.info("Added new cat " + catDto.getName());
    }

    @After("execution(* com.github.mkorman9.logic.CatLogic.updateCat(..)) && args(id,..)")
    public void logCatUpdated(Long id) {
        LOGGER.info("Updated cat with id " + id);
    }

    @After("execution(* com.github.mkorman9.logic.CatLogic.removeCat(..)) && args(id,..)")
    public void logCatRemoved(Long id) {
        LOGGER.info("Removed cat with id " + id);
    }
}
