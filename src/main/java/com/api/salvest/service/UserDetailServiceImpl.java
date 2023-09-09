package com.api.salvest.service;

import com.api.salvest.dao.UserRepository;
import com.api.salvest.model.Roles;
import com.api.salvest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Salman aka theFreakingMind
 * @date 09/09/23
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

   @Autowired
   private UserRepository userRepository;

   @Override
   public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
      User user = userRepository.findByUsername(s);
      if (user == null) throw new UsernameNotFoundException(s);

      Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
      for (Roles role : user.getRoles()) {
         grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
      }

      return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
   }
}
