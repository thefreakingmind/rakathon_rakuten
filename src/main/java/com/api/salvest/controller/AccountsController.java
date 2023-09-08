package com.api.salvest.controller;

import com.api.salvest.dao.UserRepository;
import com.api.salvest.dto.APIResponse;
import com.api.salvest.dto.AccountDTO;
import com.api.salvest.model.AccountData;
import com.api.salvest.model.Payments;
import com.api.salvest.model.User;
import com.api.salvest.service.AccountService;
import com.paytm.pg.merchant.PaytmChecksum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.RequestContextHolder;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

/**
 * @author Salman aka theFreakingMind
 * @date 07/09/23
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class AccountsController {

   private final AccountService accountService;
   private final UserRepository userRepository;

   @PostMapping("/account/create/{accountId}")
   public String createAccount(Model model, @ModelAttribute AccountDTO accountDTO, String accountId){
      List<Payments> allPaymentsForAccount = accountService.getAllPaymentsForAccount(accountId);
      model.addAttribute("data", new AccountData());
      model.addAttribute("accountData", allPaymentsForAccount);
      //TODO Replaced with thymeleaf attribute.
//      APIResponse.<AccountDTO>builder()
//              .data(accountService.createAccount(accountDTO))
//              .success(true)
//              .message("Fetched")
//              .build();

      model.addAttribute("accountData", accountService.createAccount(accountDTO));
      return "dashboard.html";
   }

   @RequestMapping("/account/dashboard")
   public List<Payments> getAllAccount(Model model){
      String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
      Optional<User> userId = userRepository.findById(sessionId);
      if(!userId.isPresent()){
         throw new RestClientException("Error in Finding account with this association");
      }
      return accountService.getAllPaymentsForAccount(sessionId);




   }


}
