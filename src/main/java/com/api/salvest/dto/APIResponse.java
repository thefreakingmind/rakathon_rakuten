package com.api.salvest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Salman aka theFreakingMind
 * @date 07/09/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class APIResponse<T> {

   private T data;
   private boolean success;
   private String message;
}
