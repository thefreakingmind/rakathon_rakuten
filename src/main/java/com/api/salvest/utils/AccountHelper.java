package com.api.salvest.utils;

import com.api.salvest.dto.AccountDTO;
import com.api.salvest.model.Account;
import com.api.salvest.model.AccountData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Salman aka theFreakingMind
 * @date 08/09/23
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountHelper {

   public AccountDTO processBuilder(Account account){
      return AccountDTO
              .builder()
              .accountReference(account.getAccountReference())
              .paymemts(null)
              .totalAmountRecieved(0.0)
              .totalAmountPending(0.0)
              .build();
   }
}
