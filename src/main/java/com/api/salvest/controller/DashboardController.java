package com.api.salvest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Salman aka theFreakingMind
 * @date 08/09/23
 */
@Controller
@Slf4j
public class DashboardController {

   @RequestMapping("/index")
   public String dashboard(Model model){
      return "index.html";
   }
}
