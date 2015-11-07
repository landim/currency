package com.crossover.exchange.model;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CurrencyRepository extends MongoRepository<Currency, String> {
	
	Currency findByCode(String code);
	@Query("{ 'name': {$regex: '?0', $options: 'i' }}")
	List<Currency> findByNameRegex(String nameRegex);
}
