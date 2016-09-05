package com.github.mkorman9.logging;

import com.github.mkorman9.logic.CatLogic;
import com.github.mkorman9.logic.dto.CatDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class CatsAuditAspect {
    @After("execution(* com.github.mkorman9.logic.CatLogic.addNewCat(..)) && args(catDto,..)")
    public void logCatAdded(CatDto catDto) {
        log.info("Added new cat " + catDto.getName());
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
