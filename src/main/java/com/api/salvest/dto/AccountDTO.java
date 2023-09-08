package com.api.salvest.dto;

import com.api.salvest.model.Payments;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

/**
 * @author Salman aka theFreakingMind
 * @date 08/09/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO {

   private String accountReference;
   @DBRef
   private List<Payments> paymemts;
   private Double totalAmountRecieved;
   private Double totalAmountPending;
   private Double totalAmountDebitted;
}
