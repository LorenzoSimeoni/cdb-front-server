package com.excilys.formation.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.dto.UserDTO;
import com.excilys.formation.exception.NotPermittedUserException;
import com.excilys.formation.mapper.MapperUser;
import com.excilys.formation.model.User;
import com.excilys.formation.service.UserService;
import com.excilys.formation.validator.ValidatorUser;

@RestController
@RequestMapping(value = "/registration")
public class RegistrationController {
	private final static Logger LOGGER = LogManager.getLogger(RegistrationController.class.getName());

	private MapperUser mapperUser;
	private ValidatorUser validatorUser;
	private UserService userService;

	
	@Autowired
	public RegistrationController(MapperUser mapperUser, ValidatorUser validatorUser, UserService userService) {
		this.mapperUser = mapperUser;
		this.validatorUser = validatorUser;
		this.userService = userService;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> registration(@RequestBody UserDTO userDTO) {
		User user = mapperUser.mapper(userDTO);
		System.out.println(user.toString());
		long nbOfUserCreated = 0;
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		try {
			validatorUser.checkUser(user);
			nbOfUserCreated = userService.create(user);
		} catch (NotPermittedUserException e) {
			LOGGER.info(" USER NOT CREATED "+e.getErrorMsg());
			return new ResponseEntity<String>("{\"error\": \"USER NOT CREATED "+e.getErrorMsg()+"\"}", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("{\"error\": \" "+nbOfUserCreated+" USER CREATED \"}", HttpStatus.CREATED);
	}
}
