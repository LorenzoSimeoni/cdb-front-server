package com.excilys.formation.validator;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.formation.exception.NameUserException;
import com.excilys.formation.exception.NotPermittedUserException;
import com.excilys.formation.exception.PasswordException;
import com.excilys.formation.exception.RoleIdException;
import com.excilys.formation.exception.UserAlreayExist;
import com.excilys.formation.model.Role;
import com.excilys.formation.model.User;
import com.excilys.formation.service.RoleService;
import com.excilys.formation.service.UserService;

@Component
public class ValidatorUser {
	private final static Logger LOGGER = LogManager.getLogger(ValidatorUser.class.getName());

	private RoleService roleService;
	private UserService userService;
	
	@Autowired
	public ValidatorUser(RoleService roleService, UserService userService) {
		this.roleService = roleService;
		this.userService = userService;
	}
	
	public void checkUser(User user) throws NotPermittedUserException {
		if (nameIsNull(user)) {
			throw new NameUserException();
		}
		if (userAlreadyExist(user)) {
			throw new UserAlreayExist();
		}
		if (!roleExist(user.getRole())) {
			throw new RoleIdException();
		}
		if (passwordIsNull(user)) {
			throw new PasswordException();
		}
		if (roleUser(user.getRole())) {
			throw new RoleIdException();
		}
	}
	
	private boolean userAlreadyExist(User user) {
		if(userService.findUserByUsername(user.getUsername()).isPresent()) {
			return true;
		}
		return false;
	}
	
	private boolean nameIsNull(User user) {
		if (user.getUsername() == null) {
			return true;			
		}
		return false;
	}
	
	private boolean passwordIsNull(User user) {
		if (user.getPassword() == null) {
			return true;
		}
		return false;
	}
	
	private boolean roleExist(Role role) {
		if(role!=null) {
			Optional<Role> optRole = roleService.findRole(role.getId());
			if(optRole.isPresent()) {
				return true;			
			} else {
				LOGGER.info("You gived a wrong RoleID, it doesn't exist !");
				return false;
			}
		}
		return true;
	}
	
	private boolean roleUser(Role role) {
		if(role!=null) {
			if(role.getId() == 2) {
				return false;
			}
		}
		return true;
	}
	
}
