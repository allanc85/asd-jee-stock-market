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
import com.javaee.maycon.stockmarket.domain.Company;
import com.javaee.maycon.stockmarket.services.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("This is a Company API")
@RestController
@RequestMapping(CompanyController.BASE_URL)
public class CompanyController {

  public static final String BASE_URL = "/api/v1/companies";

  @Autowired
  private CompanyService companyService;

  // GET

  @ApiOperation(value = "Get all companies", produces = MediaType.APPLICATION_JSON_VALUE)
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Company> findAll() {
    return companyService.findAll();
  }

  @ApiOperation(value = "Get a company by id", produces = MediaType.APPLICATION_JSON_VALUE)
  @GetMapping({"/{id}"})
  @ResponseStatus(HttpStatus.OK)
  public Company findById(@PathVariable String id) {
    return companyService.findById(id);
  }

  // POST

  @ApiOperation(value = "Create a new company", produces = MediaType.APPLICATION_JSON_VALUE)
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Company save(@RequestBody Company company) {
    return companyService.save(company);
  }

  // PUT

  @ApiOperation(value = "Update a existing company", produces = MediaType.APPLICATION_JSON_VALUE)
  @PutMapping({"/{id}"})
  @ResponseStatus(HttpStatus.OK)
  public Company update(@PathVariable String id, @RequestBody Company company) {
    return companyService.update(id, company);
  }

  // DELETE

  @ApiOperation(value = "Delete a existing company", produces = MediaType.APPLICATION_JSON_VALUE)
  @DeleteMapping({"/{id}"})
  @ResponseStatus(HttpStatus.OK)
  public void delete(@PathVariable String id) {
    companyService.deleteById(id);
  }

}
