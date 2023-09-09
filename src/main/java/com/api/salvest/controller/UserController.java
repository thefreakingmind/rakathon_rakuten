package com.api.salvest.controller;

import com.api.salvest.dao.AccountRepository;
import com.api.salvest.model.Account;
import com.api.salvest.model.User;
import com.api.salvest.service.SecurityService;
import com.api.salvest.service.UserDetailServiceImpl;
import com.api.salvest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

/**
 * @author Salman aka theFreakingMind
 * @date 09/09/23
 */
@Controller
public class UserController {

   @Autowired
   private UserService userService;

   @Autowired
   private SecurityService securityService;
   @Autowired
   private AccountRepository accountRepository;

//   @Autowired
//   private UserValidator userValidator;

   @GetMapping("/registration")
   public String registration(Model model) {
      if (securityService.isAuthenticated()) {
         return "redirect:/";
      }

      model.addAttribute("userForm", new User());

      return "registration";
   }

   @PostMapping("/registration")
   public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {

      if (bindingResult.hasErrors()) {
         return "registration";
      }
      String useraccountReference = UUID.randomUUID().toString();
      userForm.setUserReference(useraccountReference);
      Account account = Account
              .builder()
              .accountReference(useraccountReference)
              .totalAmountDebitted(0.0)
              .totalAmountPending(0.0)
              .totalAmountRecieved(0.0)
              .build();
      accountRepository.save(account);
      userService.save(userForm);

      securityService.autoLogin(userForm.getUsername(), userForm.getPassword());

         return "redirect:/welcome";
   }

   @GetMapping("/login")
   public String login(Model model, String error, String logout) {
      if (securityService.isAuthenticated()) {
         return "redirect:/user/dashboard";
      }

      if (error != null)
         model.addAttribute("error", "Your username and password is invalid.");

      if (logout != null)
         model.addAttribute("message", "You have been logged out successfully.");

      return "login";
   }

}
