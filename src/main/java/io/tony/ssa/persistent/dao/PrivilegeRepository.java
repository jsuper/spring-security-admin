package io.tony.ssa.persistent.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

import io.tony.ssa.persistent.model.Privilege;

public interface PrivilegeRepository extends CrudRepository<Privilege, Integer> {

  List<Privilege> findAll();

  Privilege findById(Integer id);

  Privilege findByName(String name);
}
