package com.api.salvest.controller;

import com.api.salvest.dto.APIResponse;
import com.api.salvest.dto.AccountDTO;
import com.api.salvest.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Salman aka theFreakingMind
 * @date 08/09/23
 */
@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountRestController {

   private final AccountService accountService;

   @GetMapping("/")
   public APIResponse<List<AccountDTO>> getAllAccounts(String Id){
      return APIResponse.<List<AccountDTO>>builder()
              .data(accountService.getAllPaymentsForAccount(Id))
              .success(true)
              .message("Fetched")
              .build();

   }
}
