package com.api.salvest.dao;

import com.api.salvest.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author Salman aka theFreakingMind
 * @date 08/09/23
 */
public interface AccountRepository extends MongoRepository<Account, String> {

   public List<Account> findAllByAccountReference(String accountId);

   public Account findAccountByAccountReference(String accountId);

}
