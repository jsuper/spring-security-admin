package io.tony.ssa.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.tony.ssa.persistent.model.Menu;
import io.tony.ssa.services.MenuService;

@Controller
public class MenuController {

  @Autowired
  private MenuService menuService;

  @RequestMapping("/menus")
  String menuIndex(Model model) {
    model.addAttribute("menus", menuService.findAll());
    return "menus";
  }

  @RequestMapping(value = "/menus/add", method = RequestMethod.POST)
  String menuAdd(Menu menu) {
    menuService.save(menu);
    return "redirect:/menus";
  }
}
