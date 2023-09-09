package com.api.salvest.controller;

import com.api.salvest.dto.ChatRequest;
import com.api.salvest.dto.Message;
import com.api.salvest.dto.ModelValidationDTO;
import com.api.salvest.service.OpenAPIService;
import com.api.salvest.utils.PaytmHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import static com.api.salvest.constant.Messages.PROMPT_FIRST;
import static com.api.salvest.constant.Messages.PROMPT_LAT;

/**
 * @author Salman aka theFreakingMind
 * @date 08/09/23
 */
@Controller
@RequiredArgsConstructor
public class RecommendationController {

   private final OpenAPIService openAPIService;
   private final PaytmHelper paytmHelper;
   private final ObjectMapper objectMapper;

   @RequestMapping("/recommendation")
   public String investmentRecommendation(Model model) throws JsonProcessingException {
      List<String> validatorData = openAPIService.processForRecommendation();
      model.addAttribute("recommendationString", validatorData);
      return "recommendation.html";

   }

}
