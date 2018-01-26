package io.tony.ssa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import io.tony.ssa.persistent.dao.UserRepository;
import io.tony.ssa.persistent.model.Role;
import io.tony.ssa.persistent.model.User;
import io.tony.ssa.web.dto.UserDto;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public List<UserDto> findAll() {
    return userRepository.findAll().stream().map(UserService::convert).collect(Collectors.toList());
  }



  private static UserDto convert(User user) {
    UserDto dto = new UserDto();
    dto.setId(user.getId());
    dto.setName(user.getName());
    dto.setPassword(user.getPassword());
    dto.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
    return dto;
  }

}
