package com.api.salvest.controller;

import com.api.salvest.dao.AccountRepository;
import com.api.salvest.dao.PaymentRepository;
import com.api.salvest.dao.UserRepository;
import com.api.salvest.dto.APIResponse;
import com.api.salvest.dto.AccountDTO;
import com.api.salvest.dto.PaymentDTO;
import com.api.salvest.model.Account;
import com.api.salvest.model.AccountData;
import com.api.salvest.model.Payments;
import com.api.salvest.model.User;
import com.api.salvest.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Salman aka theFreakingMind
 * @date 07/09/23
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class AccountsController {

   private final AccountService accountService;
   private final AccountRepository accountRepository;
   private final UserRepository userRepository;
   private final PaymentRepository paymentRepository;


   @RequestMapping("/")
   public String index(){
      return "index.html";
   }

   @RequestMapping("/user/dashboard")
   public String userDashboard(Model model){
      User byUsername = getUser();
      Account account = accountRepository.findAccountByAccountReference(byUsername.getUserReference());
      List<Payments> allPaymentForUser = accountService.getAllPaymentForUser();
      model.addAttribute("accountData", allPaymentForUser);
      model.addAttribute("amount", account);
      return "dashboard.html";
   }

   private User getUser() {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
      User byUsername = userRepository.findByUsername(userDetails.getUsername());
      return byUsername;
   }

   @GetMapping("/payment")
   public String getPayment( Model model){
      model.addAttribute("payment", new PaymentDTO());
      return "payment.html";

   }

   @PostMapping("/payment")
   public String payment(@ModelAttribute PaymentDTO paymentDTO){
      User userData = getUser();
      Account account = accountService.getAccount(userData.getUserReference());
      Payments payments = Payments
              .builder()
              .id(UUID.randomUUID().toString())
              .accountReference(account.getAccountReference())
              .paymentId(UUID.randomUUID().toString())
              .paymentAmount(paymentDTO.getPaymentAmount())
              .source(paymentDTO.getSource())
              .instrumentType(paymentDTO.getInstrumentType())
              .build();
      paymentRepository.save(payments);
      account.getPaymemts().add(payments);
      Double totalAmountRecieved = account.getTotalAmountRecieved();
      totalAmountRecieved+= paymentDTO.getPaymentAmount();;
      account.setTotalAmountRecieved(totalAmountRecieved);
      accountRepository.save(account);
      return "redirect:/user/dashboard";
   }
//
//   @RequestMapping("/account/dashboard")
//   public List<Payments> getAllAccount(Model model){
//      String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
//      Optional<User> userId = userRepository.findById(sessionId);
//      if(!userId.isPresent()){
//         throw new RestClientException("Error in Finding account with this association");
//      }
//      return accountService.getAllPaymentsForAccount(sessionId);
//
//
//
//
//   }


}
