package com.excilys.formation.dao;

import java.util.Optional;

import javax.persistence.NoResultException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.model.User;

@Repository
public class UserDAO {
	private final static Logger LOGGER = LogManager.getLogger(UserDAO.class.getName());

	private static final String FINDUSER = "FROM User WHERE username = :name AND password = :password";
	private static final String FINDUSERBYUSERNAME = "FROM User WHERE username = :name";
	
	private SessionFactory sessionFactory;
	
	@Autowired
	public UserDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Optional<User> findUser(String name, String password) {
		User user = null;
		try(Session session = sessionFactory.openSession();) {
			user = session.createQuery(FINDUSER, User.class)
					.setParameter("name", name)
					.setParameter("password", password)
					.getSingleResult();			
		} catch (NoResultException e) {
			LOGGER.info("No User Found");
		}
		return Optional.ofNullable(user);
	}
	
	public Optional<User> findUserByUsername(String name) {
		User user=null;
		try(Session session = sessionFactory.openSession();) {
			user = session.createQuery(FINDUSERBYUSERNAME, User.class)
					.setParameter("name", name)
					.getSingleResult();			
		} catch (NoResultException e) {
			LOGGER.info("No User Found");
		}
		return Optional.ofNullable(user);
	}

	public long create(User user) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.save(user);
			transaction.commit();			
		} catch (Exception e) {
			LOGGER.info("ERROR CREATING USER",e);
			transaction.rollback();
			return 0;
		} finally {
			session.close();			
		}
		return 1;
	}
	
}
