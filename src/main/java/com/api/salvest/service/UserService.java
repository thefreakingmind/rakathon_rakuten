package com.api.salvest.service;

import com.api.salvest.dao.RoleRepository;
import com.api.salvest.dao.UserRepository;
import com.api.salvest.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

/**
 * @author Salman aka theFreakingMind
 * @date 09/09/23
 */
@Service
@Slf4j
public class UserService {


   @Autowired
   private UserRepository userRepository;
   @Autowired
   private RoleRepository roleRepository;
   @Autowired
   private BCryptPasswordEncoder bCryptPasswordEncoder;


   public void save(User user) {
      user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
      user.setRoles(new HashSet<>(roleRepository.findAll()));
      userRepository.save(user);
   }

   public User findByUsername(String username) {
      return userRepository.findByUsername(username);
   }

}
