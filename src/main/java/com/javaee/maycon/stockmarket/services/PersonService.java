package com.javaee.maycon.stockmarket.services;

import java.util.List;
import com.javaee.maycon.stockmarket.domain.Person;

public interface PersonService {

	List<Person> findAll();

	Person findById(String id);

	Person save(Person person);

	Person update(String id, Person person);

	void deleteById(String id);
}
