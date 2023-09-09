package com.api.salvest.dto;

import com.api.salvest.constant.InstrumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Salman aka theFreakingMind
 * @date 09/09/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDTO {

   private String source;
   private InstrumentType instrumentType;
   private Double paymentAmount;
}
