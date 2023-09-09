package com.api.salvest.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

/**
 * @author Salman aka theFreakingMind
 * @date 07/09/23
 */
@Configuration
public class SpringCentralConfiguration {

   @Autowired
   private UserDetailsService userDetailsService;


   @Bean
   public RestTemplate restTemplate(){
      return new RestTemplate();

   }

   @Bean
   public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
      return http.getSharedObject(AuthenticationManagerBuilder.class)
              .build();
   }

   @Bean
   public ObjectMapper objectMapper(){
      return new ObjectMapper();
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Autowired
   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
   }

   @Bean
   SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

      httpSecurity
              .authorizeRequests()
              .antMatchers("/css/**", "/js/**", "/registration", "/").permitAll()
              .anyRequest().authenticated()
              .and()
              .formLogin()
              .loginPage("/login").defaultSuccessUrl("/user/dashboard")
              .permitAll()
              .and()
              .logout()
              .permitAll();
      return httpSecurity.build();
   }

}
