package com.excilys.formation.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.formation.dao.UserDAO;
import com.excilys.formation.model.User;

@Service
public class UserService implements UserDetailsService {
	private UserDAO userDAO;
	
	@Autowired
	public UserService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public Optional<User> findUser(String name, String password) {
		return userDAO.findUser(name, password);
	}
	
	public Optional<User> findUserByUsername(String name) {
		return userDAO.findUserByUsername(name);
	}
	
	public long create(User user) {
		return userDAO.create(user);
	}
	
    @Override
    public UserDetails loadUserByUsername(String username) {
    	Optional<User> user = userDAO.findUserByUsername(username);
    	UserBuilder builder = null;
    	if(!user.isPresent()) {
    		throw new UsernameNotFoundException(username);    		
    	} else {
    		builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(user.get().getPassword());
            builder.roles(user.get().getRole().getName());
    	}
        return builder.build();
    }
}
