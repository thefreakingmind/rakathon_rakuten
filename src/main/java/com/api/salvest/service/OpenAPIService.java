package com.api.salvest.service;

import com.api.salvest.dto.*;
import com.api.salvest.utils.PaytmHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.naming.HandlerRef;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.api.salvest.constant.Messages.*;

/**
 * @author Salman aka theFreakingMind
 * @date 08/09/23
 */
@RequiredArgsConstructor
@Service
public class OpenAPIService<T> {

   @Value("${openAI.api.key}")
   private String apiKey;

   private final PaytmHelper paytmHelper;
   private final ObjectMapper objectMapper;

   private final RestTemplate restTemplate;
   private String url = "https://api.openai.com/v1/chat/completions";



   private ArrayList<String> getAllLinksFromTheText(String text) {
      ArrayList<String> links = new ArrayList<>();
      Pattern p = Pattern.compile(LINK_REGEX, Pattern.CASE_INSENSITIVE);
      Matcher m = p.matcher(text);
      while (m.find()) {
         links.add(m.group());
      }
      return links;
   }

   public List<String> processForRecommendation() throws JsonProcessingException {
      Double percentage = 5.0;
      Double amount = 12.0;

      List<Message> messageList = new ArrayList<>();
      Message message = Message
              .builder()
              .role("system")
              .content(PROMPT_FIRST+" "+percentage.toString()+" % "+PROMPT_LAT+" "+amount)
              .build();
      messageList.add(message);
      ChatRequest validationDTO = ChatRequest
              .builder()
              .model("gpt-3.5-turbo")
              .messages(messageList)
              .build();
      List<String> validatorData = predictResult(validationDTO);
      String jsonString = objectMapper.writeValueAsString(validationDTO);
      System.out.println(jsonString);
      String s = validatorData.get(0);
      ArrayList<String> allLinksFromTheText = getAllLinksFromTheText(s);
      return allLinksFromTheText;
   }

   public List<String>  predictResult(ChatRequest modelValidationDTO){
      if(Objects.isNull(modelValidationDTO.getMessages()) || Objects.isNull(modelValidationDTO.getModel())){
         throw new RestClientException("Model or prompt is null, unable to process request");
      }
      HttpEntity<ChatRequest> httpEntity = getHeaders(modelValidationDTO);
      List<Choice> choices = processAPICall(url, httpEntity).getChoices();
      List<String> collect = paytmHelper.processChoices(choices);
      return collect;
   }

   public OpenAPIResponse suggestionModel(ChatRequest modelValidationDTO){
      if(Objects.isNull(modelValidationDTO.getMessages()) || Objects.isNull(modelValidationDTO.getModel())){
         throw new RestClientException("Required Parameters are not provided");
      }
      HttpEntity<ChatRequest> httpEntity = getHeaders(modelValidationDTO);
      return processAPICall(url, httpEntity);
   }

   private OpenAPIResponse processAPICall(String url, HttpEntity<ChatRequest> httpEntity) {
      ResponseEntity<OpenAPIResponse> data = restTemplate.exchange(url, HttpMethod.POST, httpEntity, OpenAPIResponse.class);
      return data.getBody();
   }

   private HttpEntity<ChatRequest> getHeaders(ChatRequest body) {
      HttpHeaders headers = new HttpHeaders();
      headers.add(HttpHeaders.AUTHORIZATION, "Bearer "+apiKey);
      System.out.println(headers );
      HttpEntity<ChatRequest> httpEntity = new HttpEntity<>(body, headers);
      return httpEntity;
   }

}
