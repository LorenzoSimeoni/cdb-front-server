package com.excilys.formation.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.dao.OrderByCompany;
import com.excilys.formation.dao.OrderByMode;
import com.excilys.formation.dto.CompanyDTO;
import com.excilys.formation.exception.IdCompanyException;
import com.excilys.formation.exception.NotPermittedCompanyException;
import com.excilys.formation.exception.WebExceptions;
import com.excilys.formation.mapper.MapperCompany;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Page;
import com.excilys.formation.service.CompanyService;
import com.excilys.formation.validator.ValidatorCompany;

@RestController
@RequestMapping(value = "/Company")
public class CompanyController {

	private final static Logger LOGGER = LogManager.getLogger(CompanyController.class.getName());

	private CompanyService companyService;
	private MapperCompany mapperCompany;
	private ValidatorCompany validatorCompany;

	@Autowired
	public CompanyController(CompanyService companyService, MapperCompany mapperCompany,
			ValidatorCompany validatorCompany) {
		this.companyService = companyService;
		this.mapperCompany = mapperCompany;
		this.validatorCompany = validatorCompany;
	}

//	@GetMapping
//	@ResponseStatus(HttpStatus.OK)
//	public List<CompanyDTO> findAllComputers() {
//		return companyService.showAll().stream().map(company -> new CompanyDTO(company)).collect(Collectors.toList());
//	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<CompanyDTO> findAllCompanies(@RequestParam(value = "order", required = false) String order,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "search", required = false) String search, @RequestParam(value = "limit") String limit,
			@RequestParam(value = "offset") String offset) {
		Page page = new Page();
		page.setLimit(Integer.parseInt(limit));
		page.setOffset(Integer.parseInt(offset));
		if (!"null".equals(search) && !search.isEmpty()) {
			return companyService
					.getCompaniesOrderByLike(OrderByCompany.myValueOf(order.toLowerCase()), OrderByMode.myValueOf(type.toLowerCase()), search, page)
					.stream().map(company -> new CompanyDTO(company)).collect(Collectors.toList());
		} else {
			return companyService
					.getCompaniesOrderBy(OrderByCompany.myValueOf(order.toLowerCase()), OrderByMode.myValueOf(type.toLowerCase()), page)
					.stream().map(company -> new CompanyDTO(company)).collect(Collectors.toList());
		}
	}

	@GetMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public CompanyDTO findOneCompany(@PathVariable("id") Long id) throws WebExceptions {
		Optional<Company> companyOpt = companyService.showDetailsById(id);
		if (companyOpt.isPresent()) {
			return new CompanyDTO(companyOpt.get());
		}
		throw new IdCompanyException();
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public int delete(@PathVariable("id") long id) throws WebExceptions {
		int nbOfCompanyDeleted = companyService.delete(id);
		if (nbOfCompanyDeleted > 0) {
			return nbOfCompanyDeleted;
		}
		throw new IdCompanyException();
	}

	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> create(@RequestBody CompanyDTO companyDTO) {
		Company company = mapperCompany.mapper(companyDTO);
		long nbOfCompanyCreated = 0;
		try {
			validatorCompany.checkCompany(company);
			nbOfCompanyCreated = companyService.create(company);
		} catch (NotPermittedCompanyException e) {
			LOGGER.info("COMPANY NOT CREATED " + e.getErrorMsg());
			return new ResponseEntity<String>("{\"error\": \"COMPANY NOT CREATED " + e.getErrorMsg() + "\"}",
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("{\"error\": \" " + nbOfCompanyCreated + " Company created \"}",
				HttpStatus.CREATED);
	}

	@PutMapping(value = "/update/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> update(@PathVariable long id, @RequestBody CompanyDTO companyDTO) {
		companyDTO.setId(id);
		long nbOfCompanyUpdated = 0;
		Company company = mapperCompany.mapper(companyDTO);
		try {
			validatorCompany.checkCompany(company);
			nbOfCompanyUpdated = companyService.update(company);
		} catch (NotPermittedCompanyException e) {
			LOGGER.info("COMPUTER NOT UPDATED " + e.getErrorMsg());
			return new ResponseEntity<String>("{\"error\": \"COMPANY NOT UPDATED " + e.getErrorMsg() + "\"}",
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("{\"error\": \" " + nbOfCompanyUpdated + " Company updated \"}",
				HttpStatus.CREATED);
	}
}
