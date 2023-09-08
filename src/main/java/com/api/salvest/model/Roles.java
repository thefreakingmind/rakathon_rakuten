package com.api.salvest.model;

import com.api.salvest.constant.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.management.relation.Role;

/**
 * @author Salman aka theFreakingMind
 * @date 07/09/23
 */
@Document("roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Roles {

   @Id
   private String id;
   private String roleReference;
   private UserRoles userRoles;

}
