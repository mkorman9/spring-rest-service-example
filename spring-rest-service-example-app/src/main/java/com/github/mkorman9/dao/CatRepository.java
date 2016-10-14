package com.github.mkorman9.dao;

import com.github.mkorman9.model.Cat;
import org.springframework.data.repository.CrudRepository;

public interface CatRepository extends CrudRepository<Cat, Long> {
}
