package io.tony.ssa.persistent.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

import io.tony.ssa.persistent.model.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {

  List<Role> findAll();

  Role findById(Integer id);

  Role findByName(String name);
}
