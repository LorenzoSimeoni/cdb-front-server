package com.excilys.formation.dao;

import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.model.Role;

@Repository
public class RoleDAO {
	private static final String FINDROLE = "FROM Role WHERE id = :id";
    
	private SessionFactory sessionFactory;
	
	@Autowired
	public RoleDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
		
	public Optional<Role> findRole(long id) {
		Session session = sessionFactory.openSession();
		Role role = session.createQuery(FINDROLE,Role.class)
				.setParameter("id", id)
				.getSingleResult();
		session.close();
		return Optional.ofNullable(role);
	}
}
