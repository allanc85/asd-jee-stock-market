package com.javaee.maycon.stockmarket.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.javaee.maycon.stockmarket.domain.Person;
import com.javaee.maycon.stockmarket.repositories.PersonRepository;

@Service
public class PersonServiceImpl implements PersonService {

  @Autowired
  private PersonRepository personRepository;

  @Override
  public List<Person> findAll() {
    return personRepository.findAll();
  }

  @Override
  public Person findById(String id) {
    return getPersonById(id);
  }

  private Person getPersonById(String id) {
    Optional<Person> personOptional = personRepository.findById(id);

    if (!personOptional.isPresent()) {
      throw new IllegalArgumentException("Person not found for ID value: " + id);
    }

    return personOptional.get();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Person save(Person person) {
    if (personRepository.findByName(person.getName()).isEmpty()) {
      return personRepository.save(person);
    } else {
      throw new IllegalArgumentException("Person already exists with name: " + person.getName());
    }
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Person update(String id, Person person) {
    person.setId(id);
    return personRepository.save(person);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void deleteById(String id) {
    personRepository.deleteById(id);
  }

}
