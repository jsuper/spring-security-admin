package io.tony.ssa;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {
  public static void main(String[] args) {
    BCryptPasswordEncoder be = new BCryptPasswordEncoder(11);
    System.out.println(be.encode("admin"));
  }
}
