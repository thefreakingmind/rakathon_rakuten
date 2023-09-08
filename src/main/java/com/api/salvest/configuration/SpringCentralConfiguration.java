package com.api.salvest.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Salman aka theFreakingMind
 * @date 07/09/23
 */
@Configuration
public class SpringCentralConfiguration {


   @Bean
   public RestTemplate restTemplate(){
      return new RestTemplate();

   }
}
