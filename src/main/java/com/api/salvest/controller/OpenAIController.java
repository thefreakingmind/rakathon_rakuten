package com.api.salvest.controller;

import com.api.salvest.dto.*;
import com.api.salvest.service.OpenAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
   public APIResponse<List<String>> promptTest(@RequestBody ChatRequest modelValidationDTO){
      return APIResponse.<List<String>>builder()
              .data(openAPIService.predictResult(modelValidationDTO))
              .success(true)
              .message("Fetched")
              .build();

   }
}
