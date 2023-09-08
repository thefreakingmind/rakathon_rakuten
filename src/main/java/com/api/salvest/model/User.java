package com.api.salvest.model;

import com.api.salvest.constant.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author Salman aka theFreakingMind
 * @date 07/09/23
 */
@Document("user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User {

   @Id
   private String id;

   private String userReference;

   private String username;

   private String password;

   @DBRef
   private List<Roles> roles;

   private UserStatus status;

   private boolean isEnabled;

}
