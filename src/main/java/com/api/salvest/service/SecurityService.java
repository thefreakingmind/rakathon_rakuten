package com.api.salvest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * @author Salman aka theFreakingMind
 * @date 09/09/23
 */
@Service
@Slf4j
public class SecurityService {

   @Autowired
   private AuthenticationManager authenticationManager;

   @Autowired
   private UserDetailsService userDetailsService;


   public boolean isAuthenticated() {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication == null || AnonymousAuthenticationToken.class.
              isAssignableFrom(authentication.getClass())) {
         return false;
      }
      return authentication.isAuthenticated();
   }

   public void autoLogin(String username, String password) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

      authenticationManager.authenticate(usernamePasswordAuthenticationToken);

      if (usernamePasswordAuthenticationToken.isAuthenticated()) {
         SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
         log.debug(String.format("Auto login %s successfully!", username));
      }
   }
}
