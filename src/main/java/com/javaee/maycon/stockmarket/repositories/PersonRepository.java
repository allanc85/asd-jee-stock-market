package com.javaee.maycon.stockmarket.repositories;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.javaee.maycon.stockmarket.domain.Person;

@Repository
public interface PersonRepository extends MongoRepository<Person, String> {

  List<Person> findByName(String name);
  
}
