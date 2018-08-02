package com.javaee.maycon.stockmarket.repositories;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.javaee.maycon.stockmarket.domain.Stock;

@Repository
public interface StockRepository extends MongoRepository<Stock, String> {

  List<Stock> findByOwnerId(String companyId);

}
