package com.api.salvest.model;

import com.api.salvest.constant.InstrumentType;
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
@Document("payments")
public class Payments {

   @Id
   private String id;

   private String paymentId;

   private String accountReference;

   private String paymentExternalId;

   private String source;

   private InstrumentType instrumentType;

   private Double paymentAmount;

}
