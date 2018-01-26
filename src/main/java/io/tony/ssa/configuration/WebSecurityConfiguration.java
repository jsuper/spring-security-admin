package io.tony.ssa.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.tony.ssa.configuration.handlers.SsaAuthenticSuccessHandler;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private SsaAuthenticSuccessHandler authenticSuccessHandler;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
      .authorizeRequests()
      .antMatchers("/login*", "/logout*", "/css/**", "/resources/**").permitAll()
      .anyRequest().authenticated().and()

      .formLogin()
      .loginPage("/login")
      .defaultSuccessUrl("/home")
      .successHandler(authenticSuccessHandler)
      .permitAll()
      .and()
      .logout()
      .deleteCookies("JSESSIONID")
      .invalidateHttpSession(true)
      .logoutUrl("/logout").permitAll();
  }

  @Autowired
  private void initialize(AuthenticationManagerBuilder builder, UserDetailsService detailsService) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(new BCryptPasswordEncoder(11));
    provider.setUserDetailsService(detailsService);
    builder.authenticationProvider(provider);
  }


  @Bean
  public BCryptPasswordEncoder createPasswordEncoder() {
    return new BCryptPasswordEncoder(11);
  }


}
