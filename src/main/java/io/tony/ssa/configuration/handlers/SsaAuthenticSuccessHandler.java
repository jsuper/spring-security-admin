package io.tony.ssa.configuration.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import io.tony.ssa.persistent.dao.MenuRepository;
import io.tony.ssa.persistent.dao.RoleRepository;
import io.tony.ssa.persistent.dao.UserRepository;
import io.tony.ssa.persistent.model.Menu;

@Component
public class SsaAuthenticSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MenuRepository menuRepository;

  @Autowired
  private RoleRepository roleRepository;


  @Override
  @Transactional
  public void onAuthenticationSuccess(HttpServletRequest request,
                                      HttpServletResponse response,
                                      Authentication authentication) throws IOException, ServletException {
    List<Menu> accessMenus ;
    if (isSuperAdmin(authentication.getAuthorities())) {
      accessMenus = menuRepository.findAll();
    } else {
      accessMenus = authentication.getAuthorities().stream().filter(role -> role.getAuthority().startsWith("ROLE_"))
        .map(role -> roleRepository.findByName(role.getAuthority())).filter(Objects::nonNull)
        .flatMap(role -> role.getMenus().stream()).distinct().sorted()
        .collect(Collectors.toList());
    }
    if (accessMenus == null) {
      accessMenus = Collections.emptyList();
    }

    request.getSession().setAttribute("userMenus", accessMenus);
    super.onAuthenticationSuccess(request, response, authentication);
  }

  private boolean isSuperAdmin(Collection<? extends GrantedAuthority> authorities) {
    for (GrantedAuthority authority : authorities) {
      if ("ROLE_ADMIN".equals(authority.getAuthority())) {
        return true;
      }
    }
    return false;
  }
}
