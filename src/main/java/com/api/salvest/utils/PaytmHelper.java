package com.api.salvest.utils;

import com.paytm.pg.merchant.PaytmChecksum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.TreeMap;

/**
 * @author Salman aka theFreakingMind
 * @date 08/09/23
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PaytmHelper {

   public String generateCheckSumForValidation(String requestBody) throws Exception {
      TreeMap<String, String> paytmParams = new TreeMap<String, String>();
      paytmParams.put("MID", "YOUR_MID_HERE");
      paytmParams.put("ORDERID", "YOUR_ORDERID_HERE");
      String paytmChecksum = PaytmChecksum.generateSignature(paytmParams, "YOUR_MERCHANT_KEY");
      return paytmChecksum;

   }
}
