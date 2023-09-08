package com.api.salvest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author Salman aka theFreakingMind
 * @date 08/09/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("tax_regime")
public class TaxModel {

   @Id
   private String id;

   private String accountReference;

   private Double taxCalculated;

   private LocalDateTime lastUpdated;
   private LocalDateTime created;
}
