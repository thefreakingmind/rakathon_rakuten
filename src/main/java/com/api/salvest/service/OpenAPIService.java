package com.api.salvest.service;

import com.api.salvest.dto.ModelValidationDTO;
import com.api.salvest.dto.OpenAIPromptDTO;
import lombok.RequiredArgsConstructor;
import org.apache.naming.HandlerRef;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author Salman aka theFreakingMind
 * @date 08/09/23
 */
@RequiredArgsConstructor
@Service
public class OpenAPIService {

   @Value("${openAI.api.key}")
   private String apiKey;

   private final RestTemplate restTemplate;
   private String url = "https://api.openai.com/v1/models";


   public Map<String, Object> predictResult(OpenAIPromptDTO modelValidationDTO){
      HttpHeaders headers = new HttpHeaders();
      headers.add(HttpHeaders.AUTHORIZATION, "Bearer"+apiKey);
      headers.add("OpenAI-Organization", "org-wFCTuuet1SG1pC8Jzua7ZAu0");
      HttpEntity<String> httpEntity = new HttpEntity<>(headers);

      ResponseEntity<Map<String, Object>> data = restTemplate.exchange(url, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<Map<String, Object>>() {});
      return data.getBody();
   }

}
