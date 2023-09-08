package com.api.salvest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Salman aka theFreakingMind
 * @date 08/09/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("account_data")
public class AccountData {

   @Id
   private String id;

   private String accountReference;
   private String accountName;
   private Double totalBalancePending;
   private Double totalCredit;
   private Double totalRecieved;
}
