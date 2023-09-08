package com.api.salvest.dao;

import com.api.salvest.model.TaxModel;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Salman aka theFreakingMind
 * @date 08/09/23
 */
public interface TaxRepository extends MongoRepository<TaxModel, String> {

   public TaxModel findByAccountReference(String accountReference);

}
