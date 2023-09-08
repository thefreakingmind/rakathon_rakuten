package com.api.salvest.controller;

import com.api.salvest.dto.APIResponse;
import com.api.salvest.dto.ModelValidationDTO;
import com.api.salvest.dto.OpenAIPromptDTO;
import com.api.salvest.service.OpenAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Salman aka theFreakingMind
 * @date 08/09/23
 */
@RestController
@RequestMapping("/api/v1/model")
@RequiredArgsConstructor
public class OpenAIController {

   private final OpenAPIService openAPIService;

   @RequestMapping(value = "/predict", method = RequestMethod.POST)
   public APIResponse<Map<String, Object>> promptTest(@RequestBody OpenAIPromptDTO modelValidationDTO){
      return APIResponse.<Map<String, Object>>builder()
              .data(openAPIService.predictResult(modelValidationDTO))
              .success(true)
              .message("Fetched")
              .build();

   }
}
