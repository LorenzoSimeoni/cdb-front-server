package com.excilys.formation.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.excilys.formation.dto.ComputerDTO;
import com.excilys.formation.exception.IdException;
import com.excilys.formation.exception.NotPermittedComputerException;
import com.excilys.formation.exception.WebExceptions;
import com.excilys.formation.mapper.MapperComputer;
import com.excilys.formation.model.Computer;
import com.excilys.formation.service.ComputerService;
import com.excilys.formation.validator.ValidatorComputer;

@RestController
@RequestMapping(value="/Computer")
public class ComputerController {
	
	private final static Logger LOGGER = LogManager.getLogger(ComputerController.class.getName());
	
	private ComputerService computerService;
	private MapperComputer mapperComputer;
	private ValidatorComputer validatorComputer;
	
	@Autowired
	public ComputerController(ComputerService computerService, MapperComputer mapperComputer, ValidatorComputer validatorComputer) {
		this.computerService = computerService;
		this.mapperComputer = mapperComputer;
		this.validatorComputer = validatorComputer;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Computer> findAllComputers() {
		return computerService.showComputer();
	}
	
	@GetMapping(value= "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Computer findOneComputer(@PathVariable("id") Long id) throws WebExceptions {
		Optional<Computer> computerOpt = computerService.showComputerDetailsByID(id);
		if(computerOpt.isPresent()) {
			return computerOpt.get();
		}
		throw new IdException();
	}
	
	@DeleteMapping(value="/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable("id") long id) {
		computerService.deleteComputer(id);
	}
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public long create(@RequestBody ComputerDTO computerDTO) {
		Computer computer = mapperComputer.mapper(computerDTO);
		long nbOfComputerCreated = 0;
		try {
			validatorComputer.checkComputer(computer);
			nbOfComputerCreated = computerService.createComputer(computer);
		} catch (NotPermittedComputerException e) {
			LOGGER.info(" COMPUTER NOT CREATED "+e.getErrorMsg(),e);
		}
		return nbOfComputerCreated;
	}
	
	@PutMapping(value="/update/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable long id,@RequestBody ComputerDTO computerDTO) {
		Computer computer = mapperComputer.mapper(computerDTO);
		try {
			validatorComputer.checkComputer(computer);
			computerService.updateComputer(computer);
		} catch (NotPermittedComputerException e) {
			LOGGER.info(" COMPUTER NOT UPDATED "+e.getErrorMsg(),e);
		}
	}
}
