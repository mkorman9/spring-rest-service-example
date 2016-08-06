package com.github.mkorman9.logic;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SomeCachableLogic {
    @Cacheable("someCacheRegion")
    public String someMethod() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {
        }

        return "result";
    }
}
