package com.api.salvest.service;

import com.api.salvest.dao.UserRepository;
import com.api.salvest.dto.UserDTO;
import com.api.salvest.model.Roles;
import com.api.salvest.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Salman aka theFreakingMind
 * @date 08/09/23
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailService implements UserDetails {

   private final UserRepository userRepository;

   private User user;


   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      List<Roles> roles = user.getRoles();
      List<SimpleGrantedAuthority> authorities = new ArrayList<>();

      for (Roles role : roles) {
         authorities.add(new SimpleGrantedAuthority(role.getUserRoles().toString()));
      }

      return authorities;
   }

   @Override
   public String getPassword() {
      return user.getPassword();
   }

   @Override
   public String getUsername() {
      return user.getUsername();
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return false;
   }

   //TODO Expiry time to be set to 1 Day.
   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return user.isEnabled();
   }
}
