package com.api.salvest.configuration;

import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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


   @Bean
   public RestTemplate restTemplate(){
      return new RestTemplate();

   }

   //TODO Removing for now will add later
//   @Bean
//   public PasswordEncoder passwordEncoder() {
//      return new BCryptPasswordEncoder();
//   }

   @Bean
   SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

      httpSecurity.formLogin(Customizer.withDefaults());
      httpSecurity.csrf().disable();
      httpSecurity.headers().frameOptions().disable();
      httpSecurity
              .authorizeRequests()
              .antMatchers("/test").hasAuthority("ADMIN")
              .antMatchers("/api/v1/model/predict").hasAuthority("ADMIN")
              .antMatchers("/home")
              .permitAll()
              .anyRequest().authenticated();
      return httpSecurity.build();
   }

}
