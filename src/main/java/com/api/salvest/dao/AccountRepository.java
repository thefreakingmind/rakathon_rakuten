package com.api.salvest.dao;

import com.api.salvest.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Salman aka theFreakingMind
 * @date 08/09/23
 */
public interface AccountRepository extends MongoRepository<Account, String> {

   public Account findAccountByAccountReferenceAfter(String accountId);

}
