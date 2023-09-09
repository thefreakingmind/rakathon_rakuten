package com.api.salvest.dao;

import com.api.salvest.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Salman aka theFreakingMind
 * @date 07/09/23
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

   public User findByUsername(String username);


}
