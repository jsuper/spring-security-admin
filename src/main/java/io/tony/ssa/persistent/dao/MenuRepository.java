package io.tony.ssa.persistent.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

import io.tony.ssa.persistent.model.Menu;

public interface MenuRepository extends CrudRepository<Menu, Integer> {
  List<Menu> findAll();
}
