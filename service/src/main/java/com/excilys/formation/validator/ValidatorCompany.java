package com.excilys.formation.validator;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.formation.exception.CompanyAlreadyExistException;
import com.excilys.formation.exception.NameCompanyException;
import com.excilys.formation.exception.NotPermittedCompanyException;
import com.excilys.formation.model.Company;
import com.excilys.formation.service.CompanyService;

@Component
public class ValidatorCompany {
	
	private CompanyService companyService;
	
	@Autowired
	public ValidatorCompany(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	public void checkCompany(Company company) throws NotPermittedCompanyException {
		if(companyNameEmptyOrNull(company)) {
			throw new NameCompanyException();
		}
		if(companyExist(company)) {
			throw new CompanyAlreadyExistException();
		}
	}
	
	private boolean companyNameEmptyOrNull(Company company) {
		if(company.getName() == null || company.getName().equals("")) {
			return true;
		}
		return false;
	}
	private boolean companyExist(Company company) {
		Optional<Company> optCompany = companyService.showDetailsByName(company.getName());
		if(optCompany.isPresent()) {
			return true;
		}
		return false;
	}
}
