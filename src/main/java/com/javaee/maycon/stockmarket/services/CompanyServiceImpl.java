package com.javaee.maycon.stockmarket.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.javaee.maycon.stockmarket.domain.Company;
import com.javaee.maycon.stockmarket.repositories.CompanyRepository;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Override
	public List<Company> findAll() {
		return companyRepository.findAll();
	}

	@Override
	public Company findById(String id) {
		return getCompanyById(id);
	}

	private Company getCompanyById(String id) {
		Optional<Company> companyOptional = companyRepository.findById(id);

		if (!companyOptional.isPresent()) {
			throw new IllegalArgumentException("Company not found for ID value: " + id.toString());
		}

		return companyOptional.get();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Company save(Company company) {
		if (companyRepository.findByName(company.getName()).isEmpty()) {
			return companyRepository.save(company);
		} else {
			throw new IllegalArgumentException("Company already exists with name: " + company.getName());
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Company update(String id, Company company) {
		company.setId(id);
		return companyRepository.save(company);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteById(String id) {
		companyRepository.deleteById(id);
	}

}
