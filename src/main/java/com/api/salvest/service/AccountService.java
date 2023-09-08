package com.api.salvest.service;

import com.api.salvest.dao.AccountRepository;
import com.api.salvest.dao.PaymentRepository;
import com.api.salvest.dao.TaxRepository;
import com.api.salvest.dto.AccountDTO;
import com.api.salvest.model.Account;
import com.api.salvest.model.Payments;
import com.api.salvest.model.TaxModel;
import com.api.salvest.utils.AccountHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

   public AccountDTO createAccount(AccountDTO accountDTO){
      Account account = new Account();
      BeanUtils.copyProperties(account, accountDTO);
      Account save = accountRepository.save(account);
      return accountHelper.processBuilder(account);
   }

   public List<Payments> getAllPaymentsForAccount(String userId){
      String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
      Account accountByAccountReferenceAfter = accountRepository.findAccountByAccountReferenceAfter(sessionId);
      return accountByAccountReferenceAfter.getPaymemts();


   }
   
   public Account processPaymentForAccount(String paymentId){
      Optional<Payments> bypaymentId = Optional.ofNullable(paymentRepository.findBypaymentId(paymentId));
      if(!bypaymentId.isPresent()){
         throw new RestClientException("Payment has not been associated from this paymentId");
      }
      Payments pay = bypaymentId.get();
      processPayments(pay);
      Account account = accountRepository.findAccountByAccountReferenceAfter(bypaymentId.get().getAccountReference());
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

   }

   /**
    * Calculate Tax at the end of the payment. 
    * @param payments
    */
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


}
