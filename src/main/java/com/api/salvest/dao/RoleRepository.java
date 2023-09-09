package com.api.salvest.dao;

import com.api.salvest.constant.UserRoles;
import com.api.salvest.model.Roles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import java.util.Optional;

/**
 * @author Salman aka theFreakingMind
 * @date 07/09/23
 */
public interface RoleRepository extends MongoRepository<Roles, String> {

   public Optional<Roles> findById(String Id);
//   public Optional<Roles> findByUserRoles(UserRoles roles);

}
