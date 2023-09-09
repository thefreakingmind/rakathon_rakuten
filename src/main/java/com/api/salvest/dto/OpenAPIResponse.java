package com.api.salvest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Salman aka theFreakingMind
 * @date 08/09/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpenAPIResponse {
   private String id;
   private String object;
   private long created;
   private String model;
   private List<Choice> choices;
   private Usage usage;
}
