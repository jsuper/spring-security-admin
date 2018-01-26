package io.tony.ssa.web.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserDto {

  private Integer id;
  private String name;
  private String password;
  private List<String> roles;
}
