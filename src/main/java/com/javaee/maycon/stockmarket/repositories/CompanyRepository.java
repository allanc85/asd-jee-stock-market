package com.javaee.maycon.stockmarket.repositories;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.javaee.maycon.stockmarket.domain.Company;

@Repository
public interface CompanyRepository extends MongoRepository<Company, String> {

  List<Company> findByName(String name);
  
}
