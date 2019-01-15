package com.excilys.formation.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.dto.CompanyDTO;
import com.excilys.formation.exception.IdException;
import com.excilys.formation.exception.NotPermittedCompanyException;
import com.excilys.formation.exception.WebExceptions;
import com.excilys.formation.mapper.MapperCompany;
import com.excilys.formation.model.Company;
import com.excilys.formation.service.CompanyService;
import com.excilys.formation.validator.ValidatorCompany;

@RestController
@RequestMapping(value="/Company")
public class CompanyController {
	
	private final static Logger LOGGER = LogManager.getLogger(CompanyController.class.getName());

	private CompanyService companyService;
	private MapperCompany mapperCompany;
	private ValidatorCompany validatorCompany;
	
	@Autowired
	public CompanyController(CompanyService companyService, MapperCompany mapperCompany, ValidatorCompany validatorCompany) {
		this.companyService = companyService;
		this.mapperCompany = mapperCompany;
		this.validatorCompany = validatorCompany;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Company> findAllComputers() {
		return companyService.showAll();
	}
	
	@GetMapping(value= "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Company findOneCompany(@PathVariable("id") Long id) throws WebExceptions {
		Optional<Company> companyOpt = companyService.showDetailsById(id);
		if(companyOpt.isPresent()) {
			return companyOpt.get();
		}
		throw new IdException();
	}
	
	@DeleteMapping(value="/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable("id") long id) {
		companyService.delete(id);
	}
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public long create(@RequestBody CompanyDTO companyDTO) {
		Company company = mapperCompany.mapper(companyDTO);
		long nbOfCompanyCreated = 0;
		try {
			validatorCompany.checkCompany(company);
			nbOfCompanyCreated = companyService.create(company);
		} catch (NotPermittedCompanyException e) {
			LOGGER.info(" COMPANY NOT CREATED "+e.getErrorMsg());
		}
		return nbOfCompanyCreated;
	}
	
	@PutMapping(value="/update/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable long id,@RequestBody CompanyDTO companyDTO) {
		companyDTO.setId(id);
		Company company = mapperCompany.mapper(companyDTO);
		try {
			validatorCompany.checkCompany(company);
			companyService.update(company);
		} catch (NotPermittedCompanyException e) {
			LOGGER.info(" COMPUTER NOT UPDATED "+e.getErrorMsg());
		}
	}
}
