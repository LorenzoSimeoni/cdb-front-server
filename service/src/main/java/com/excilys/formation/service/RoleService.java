package com.excilys.formation.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.dao.RoleDAO;
import com.excilys.formation.model.Role;

@Service
public class RoleService {
	private RoleDAO roleDAO;
	
	@Autowired
	public RoleService(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}
	
	public Optional<Role> findRole(Long id) {
		return roleDAO.findRole(id);
	}
}
