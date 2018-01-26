package io.tony.ssa.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import io.tony.ssa.persistent.dao.PrivilegeRepository;
import io.tony.ssa.persistent.dao.RoleRepository;
import io.tony.ssa.persistent.dao.UserRepository;
import io.tony.ssa.persistent.model.Privilege;
import io.tony.ssa.persistent.model.Role;
import io.tony.ssa.persistent.model.User;

@Component
public class InitialBasicDataListener implements ApplicationListener<ContextRefreshedEvent> {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PrivilegeRepository privilegeRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;


  private static boolean run;

  @Override
  @Transactional
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    if (run) {
      return;
    }

    Privilege read = createPrivilegeIfNotFound("READ_PRIVILEGE");
    Privilege write = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

    Role userRole = createRoleIfNotFound("ROLE_USER", Collections.singletonList(read));
    Role adminRole = createRoleIfNotFound("ROLE_ADMIN", Arrays.asList(read, write));

    User admin = createUserIfNotFound("admin", passwordEncoder.encode("admin"), Arrays.asList(userRole, adminRole));
    User user = createUserIfNotFound("user", passwordEncoder.encode("user"), Collections.singletonList(userRole));
  }

  private Privilege createPrivilegeIfNotFound(String name) {
    Privilege privilege = privilegeRepository.findByName(name);
    if (privilege == null) {
      privilege = new Privilege();
      privilege.setName(name);
      privilege = privilegeRepository.save(privilege);
    }
    return privilege;
  }

  private Role createRoleIfNotFound(String name, List<Privilege> privileges) {
    Role role = roleRepository.findByName(name);
    if (role == null) {
      role = new Role();
      role.setName(name);
      role.setPrivileges(privileges);
      role = roleRepository.save(role);
    }
    return role;
  }

  private User createUserIfNotFound(String name, String password, List<Role> roles) {
    User user = userRepository.findByName(name);
    if (user == null) {
      user = new User();
      user.setName(name);
      user.setPassword(password);
      user.setRoles(roles);
      user = userRepository.save(user);
    }

    return user;
  }
}
