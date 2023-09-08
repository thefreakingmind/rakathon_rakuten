package com.api.salvest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Salman aka theFreakingMind
 * @date 08/09/23
 */
@Controller
public class HomeController {

   @RequestMapping("/")
   public String index(){
      return "index.html";

   }
}
