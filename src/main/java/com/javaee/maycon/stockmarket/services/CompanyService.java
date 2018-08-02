package com.javaee.maycon.stockmarket.services;

import java.util.List;
import com.javaee.maycon.stockmarket.domain.Company;

public interface CompanyService {

	List<Company> findAll();

	Company findById(String id);

	Company save(Company company);

	Company update(String id, Company company);

	void deleteById(String id);
}
