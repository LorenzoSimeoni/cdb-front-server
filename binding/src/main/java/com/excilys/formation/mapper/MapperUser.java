package com.excilys.formation.mapper;

import org.springframework.stereotype.Component;

import com.excilys.formation.checker.Controller;
import com.excilys.formation.dto.UserDTO;
import com.excilys.formation.model.Role;
import com.excilys.formation.model.User;

@Component
public class MapperUser {
	public User mapper(UserDTO userDTO) {
		User user = new User();
		Role role = new Role();
		user.setName(userDTO.getName());
		user.setPassword(userDTO.getPassword());
		if(Controller.testStringIsALong(userDTO.getRoleId())) {			
			role.setId(Long.parseLong(userDTO.getRoleId()));
		}
		if(userDTO.getRoleName() != null) {
			role.setName(userDTO.getRoleName());			
		}
		user.setRole(role);
		return user;
	}
}
