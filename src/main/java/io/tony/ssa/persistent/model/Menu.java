package io.tony.ssa.persistent.model;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;

@Entity
@Data
public class Menu implements Comparator<Menu> {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String name;
  private String url;
  private String icon;

  @ManyToMany(mappedBy = "menus")
  private List<Role> roles;

  @Override
  public int compare(Menu o1, Menu o2) {
    return o1.getId().compareTo(o2.getId());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    Menu menu = (Menu) o;
    return Objects.equals(id, menu.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), id, name, url, icon, roles);
  }
}
