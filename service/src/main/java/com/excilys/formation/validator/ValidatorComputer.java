package com.excilys.formation.validator;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.formation.exception.CompanyIDException;
import com.excilys.formation.exception.DateDiscontinuedIsBeforIntroducedException;
import com.excilys.formation.exception.DateDiscontinuedWithoutIntroduced;
import com.excilys.formation.exception.NameComputerException;
import com.excilys.formation.exception.NotPermittedComputerException;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;
import com.excilys.formation.service.CompanyService;

@Component
public class ValidatorComputer {
	private final static Logger LOGGER = LogManager.getLogger(ValidatorComputer.class.getName());
	
	private CompanyService companyServices;
	
	@Autowired
	public ValidatorComputer(CompanyService companyServices) {
		this.companyServices = companyServices;
	}
	
	public void checkComputer(Computer computer) throws NotPermittedComputerException {
		if(nameIsNull(computer)) {
			throw new NameComputerException();
		}
		if(!companyExist(computer.getCompany())) {
			throw new CompanyIDException();
		}
		if(discontinuedNotNullWhileIntroducedIs(computer)) {
			throw new DateDiscontinuedWithoutIntroduced();
		}
		if(discontinuedNotAfterIntroduced(computer)) {
			throw new DateDiscontinuedIsBeforIntroducedException();
		}
	}
	
	private boolean nameIsNull(Computer computer) {
		if(computer.getName() == null) {
			return true;			
		}
		return false;
	}
	
	private boolean discontinuedNotNullWhileIntroducedIs(Computer computer) {
		if(computer.getDiscontinued() != null && computer.getIntroduced() == null) {
			return true;
		}
		return false;
	}
	
	private boolean discontinuedNotAfterIntroduced(Computer computer) {
		if(computer.getIntroduced() != null && computer.getDiscontinued() != null) {
			if (!computer.getDiscontinued().isBefore(computer.getIntroduced())) {
				return true;
			}
		}
		return false;
	}
	
	private boolean companyExist(Company company) {
		if(company!=null) {
			Optional<Company> optCompany = companyServices.showDetailsById(company.getId());
			if(optCompany.isPresent()) {
				return true;			
			} else {
				LOGGER.info("You gived a wrong CompanyID, it doesn't exist !");
				return false;
			}
		}
		return true;
	}
}
