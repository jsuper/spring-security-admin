package io.tony.ssa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import io.tony.ssa.persistent.dao.MenuRepository;
import io.tony.ssa.persistent.model.Menu;

@Service
public class MenuService {

  @Autowired
  private MenuRepository menuRepository;

  public List<Menu> findAll() {
    return menuRepository.findAll();
  }

  public Menu save(Menu menu) {
    if (menu.getIcon() == null) {
      menu.setIcon("home");
    }
    return menuRepository.save(menu);
  }
}
