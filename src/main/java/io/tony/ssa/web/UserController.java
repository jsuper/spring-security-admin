package io.tony.ssa.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import io.tony.ssa.services.UserService;
import io.tony.ssa.web.dto.UserDto;

@Controller
public class UserController {

  @Autowired
  private UserService uds;

  @RequestMapping("/users")
  String userIndex(Model model) {
    List<UserDto> allUsers = uds.findAll();
    model.addAttribute("users", allUsers);
    return "users";
  }
}
