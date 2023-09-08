package com.api.salvest.model;

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
 * @date 08/09/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("account")
public class Account {

   @Id
   private String id;
   private String accountReference;
   @DBRef
   private List<Payments> paymemts;
   private Double totalAmountRecieved;
   private Double totalAmountPending;
   private Double totalAmountDebitted;


}
