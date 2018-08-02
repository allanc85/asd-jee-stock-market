package com.javaee.maycon.stockmarket.controllers.v1;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.javaee.maycon.stockmarket.domain.Person;
import com.javaee.maycon.stockmarket.services.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("This is a Person API")
@RestController
@RequestMapping(PersonController.BASE_URL)
public class PersonController {

  public static final String BASE_URL = "/api/v1/persons";

  @Autowired
  private PersonService personService;

  // GET

  @ApiOperation(value = "Get all persons", produces = MediaType.APPLICATION_JSON_VALUE)
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Person> findAll() {
    return personService.findAll();
  }

  @ApiOperation(value = "Get a person by id", produces = MediaType.APPLICATION_JSON_VALUE)
  @GetMapping({"/{id}"})
  @ResponseStatus(HttpStatus.OK)
  public Person findById(@PathVariable String id) {
    return personService.findById(id);
  }

  // POST

  @ApiOperation(value = "Create a new person", produces = MediaType.APPLICATION_JSON_VALUE)
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Person save(@RequestBody Person person) {
    return personService.save(person);
  }

  // PUT

  @ApiOperation(value = "Update a existing person", produces = MediaType.APPLICATION_JSON_VALUE)
  @PutMapping({"/{id}"})
  @ResponseStatus(HttpStatus.OK)
  public Person update(@PathVariable String id, @RequestBody Person person) {
    return personService.update(id, person);
  }

  // DELETE

  @ApiOperation(value = "Delete a existing person", produces = MediaType.APPLICATION_JSON_VALUE)
  @DeleteMapping({"/{id}"})
  @ResponseStatus(HttpStatus.OK)
  public void delete(@PathVariable String id) {
    personService.deleteById(id);
  }

}
