package io.tony.ssa.persistent.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

import io.tony.ssa.persistent.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

  User findById(Integer id);

  List<User> findAll();

  User findByName(String name);
}
