package com.api.salvest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Salman aka theFreakingMind
 * @date 08/09/23
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
   private String role;
   private String content;

}
