package com.excilys.formation.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.excilys.formation.dao.OrderByComputer;
import com.excilys.formation.dao.OrderByMode;
import com.excilys.formation.dto.ComputerDTO;
import com.excilys.formation.exception.IdComputerException;
import com.excilys.formation.exception.NotPermittedComputerException;
import com.excilys.formation.exception.WebExceptions;
import com.excilys.formation.mapper.MapperComputer;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.Page;
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
	public List<ComputerDTO> findAllCompanies(@RequestParam(value = "order", required = false) String order,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "search", required = false) String search, @RequestParam(value = "limit") String limit,
			@RequestParam(value = "offset") String offset) {
		Page page = new Page();
		if (!"null".equals(limit) || limit.isEmpty()) {
			page.setLimit(Integer.parseInt(limit));
		}
		if (!"null".equals(offset) || offset.isEmpty()) {
			page.setOffset(Integer.parseInt(offset));
		}
		if (!"null".equals(search) && !search.isEmpty()) {
			return computerService
					.getComputerOrderByLike(OrderByComputer.myValueOf(order.toLowerCase()), OrderByMode.myValueOf(type.toLowerCase()), search, page)
					.stream().map(computer -> new ComputerDTO(computer)).collect(Collectors.toList());
		} else {
			return computerService
					.getListOrderBy(OrderByComputer.myValueOf(order.toLowerCase()), OrderByMode.myValueOf(type.toLowerCase()), page)
					.stream().map(computer -> new ComputerDTO(computer)).collect(Collectors.toList());
		}
	}
	
	@GetMapping(value= "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ComputerDTO findOneComputer(@PathVariable("id") Long id) throws WebExceptions {
		Optional<Computer> computerOpt = computerService.showComputerDetailsByID(id);
		if(computerOpt.isPresent()) {
			return new ComputerDTO(computerOpt.get());
		}
		throw new IdComputerException();
	}

	@GetMapping("/count")
	@ResponseStatus(HttpStatus.OK)
	public long getComputerNumber() {
		return computerService.countComputer();
	}

	@GetMapping("/searchCount")
	@ResponseStatus(HttpStatus.OK)
	public long getComputerNumberLike(@RequestParam(value = "search") String search) {
		return computerService.countComputerLike(search);
	}

	@GetMapping("/countFromCompany")
	@ResponseStatus(HttpStatus.OK)
	public long getComputerNumberFromCompanyId(@RequestParam(value = "id") long id) {
		return computerService.countComputerFromCompanyId(id);
	}
	
	@DeleteMapping(value="/{id}")
	@ResponseStatus(HttpStatus.OK)
	public int delete(@PathVariable("id") long id) throws WebExceptions {
		int nbOfComputerDeleted = computerService.deleteComputer(id);
		if(nbOfComputerDeleted>0) {
			return nbOfComputerDeleted;
		}
		throw new IdComputerException();
	}
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> create(@RequestBody ComputerDTO computerDTO) {
		Computer computer = mapperComputer.mapper(computerDTO);
		long nbOfComputerCreated = 0;
		try {
			validatorComputer.checkComputer(computer);
			nbOfComputerCreated = computerService.createComputer(computer);
		} catch (NotPermittedComputerException e) {
			LOGGER.info("COMPUTER NOT CREATED "+e.getErrorMsg());
			return new ResponseEntity<String>("{\"error\": \"COMPUTER NOT CREATED "+e.getErrorMsg()+"\"}", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("{\"error\": \" "+nbOfComputerCreated+" Computer created \"}", HttpStatus.CREATED);
	}
	
	@PutMapping(value="/update/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> update(@PathVariable long id,@RequestBody ComputerDTO computerDTO) {
		Computer computer = mapperComputer.mapper(computerDTO);
		long nbOfComputerUpdated = 0;
		try {
			validatorComputer.checkComputer(computer);
			computerService.updateComputer(computer);
		} catch (NotPermittedComputerException e) {
			LOGGER.info("COMPUTER NOT UPDATED "+e.getErrorMsg());
			return new ResponseEntity<String>("{\"error\": \"COMPUTER NOT UPDATED "+e.getErrorMsg()+"\"}", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("{\"error\": \" "+nbOfComputerUpdated+" Computer updated \"}", HttpStatus.ACCEPTED);
	}
}
