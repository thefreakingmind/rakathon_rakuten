package com.api.salvest.dao;

import com.api.salvest.model.Payments;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Salman aka theFreakingMind
 * @date 08/09/23
 */
public interface PaymentRepository extends MongoRepository<Payments, String> {

   public Payments findBypaymentId(String paymentId);
}
