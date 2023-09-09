package com.api.salvest.service;

import com.api.salvest.dao.AccountRepository;
import com.api.salvest.dao.PaymentRepository;
import com.api.salvest.dao.TaxRepository;
import com.api.salvest.dao.UserRepository;
import com.api.salvest.dto.AccountDTO;
import com.api.salvest.model.Account;
import com.api.salvest.model.Payments;
import com.api.salvest.model.TaxModel;
import com.api.salvest.model.User;
import com.api.salvest.utils.AccountHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Salman aka theFreakingMind
 * @date 07/09/23
 */
@Service
@RequiredArgsConstructor
public class AccountService {


   private final AccountRepository accountRepository;
   private final AccountHelper accountHelper;
   private final PaymentRepository paymentRepository;
   private final TaxRepository taxRepository;
   private final UserRepository userRepository;

   public AccountDTO createAccount(AccountDTO accountDTO){
      Account account = new Account();
      BeanUtils.copyProperties(account, accountDTO);
      Account save = accountRepository.save(account);
      return accountHelper.processBuilder(account);
   }



   public List<AccountDTO> getAllPaymentsForAccount(String userId){
      String sessionId = RequestContextHolder.getRequestAttributes(  ).getSessionId();
      List<Account> accountByAccountReferenceAfter = accountRepository.findAllByAccountReference(sessionId);
      if(accountByAccountReferenceAfter.isEmpty()){
         throw new RestClientException("Payments are not found for this account");
      }
      return accountByAccountReferenceAfter
              .stream()
              .map(accountHelper::processBuilder)
              .collect(Collectors.toList());

   }
   
   public Account processPaymentForAccount(String paymentId){
      Optional<Payments> bypaymentId = Optional.ofNullable(paymentRepository.findBypaymentId(paymentId));
      if(!bypaymentId.isPresent()){
         throw new RestClientException("Payment has not been associated from this paymentId");
      }
      Payments pay = bypaymentId.get();
      processPayments(pay);
      Account account = accountRepository.findAccountByAccountReference(bypaymentId.get().getAccountReference());
      account.setTotalAmountDebitted(account.getTotalAmountPending());
      return accountRepository.save(account);
   }

   private void processPayments(Payments payments) {
      if(payments.getPaymentAmount() > 700000){
         processAmountForTax(payments);
      }else {
         processGeneralPayment(payments);
      }
   }

   private void processGeneralPayment(Payments payments) {
      String paymentId = payments.getPaymentId();


   }
   private TaxModel processAmountForTax(Payments payments) {
      Optional<TaxModel> byAccountReference = Optional.ofNullable(taxRepository.findByAccountReference(payments.getAccountReference()));
      if(byAccountReference.isPresent()){
         TaxModel taxModel = byAccountReference.get();
         Double currentTaxCalculation = 0.05* payments.getPaymentAmount();
         double v = byAccountReference.get().getTaxCalculated() + currentTaxCalculation;
         return taxRepository.save(taxModel);
      }else{
         TaxModel taxModel = TaxModel
                 .builder()
                 .taxCalculated(0.05*payments.getPaymentAmount())
                 .accountReference(payments.getAccountReference())
                 .lastUpdated(LocalDateTime.now())
                 .build();
         return taxRepository.save(taxModel);
      }
   }

   public Account getAccount(String Id) {
      return accountRepository.findAccountByAccountReference(Id);
   }

   public List<Payments> getAllPaymentForUser(){
      User user = getUser();
      List<Account> allByAccountReference = accountRepository.findAllByAccountReference(user.getUserReference());
      List<List<Payments>> collect = allByAccountReference
              .stream()
              .map(x -> x.getPaymemts())
              .collect(Collectors.toList());
      List<Payments> paymentsList = collect
              .stream()
              .flatMap(List::stream)
              .collect(Collectors.toList());
      return paymentsList;
   }

   private User getUser() {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
      User byUsername = userRepository.findByUsername(userDetails.getUsername());
      return byUsername;
   }


   public List<Payments> getAllPayments() {
      return paymentRepository.findAll();
   }
}
