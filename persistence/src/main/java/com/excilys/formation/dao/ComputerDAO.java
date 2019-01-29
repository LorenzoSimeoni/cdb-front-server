package com.excilys.formation.dao;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.model.Computer;
import com.excilys.formation.model.Page;

/**
 * 
 * @author excilys
 *
 */
@Repository
public class ComputerDAO {

	private final static Logger LOGGER = LogManager.getLogger(ComputerDAO.class.getName());

	private static final String LISTCOMPUTER = "FROM Computer";
	private static final String SHOWCOMPUTERDETAILS = "FROM Computer WHERE name = :name";
	private static final String SHOWCOMPUTERDETAILSBYID = "FROM Computer WHERE id = :id";
	private static final String SEARCHCOMPUTERANDCOMPANY = "select cpu from Computer as cpu left join Company as cpa with cpu.company = cpa.id where cpu.name like :nameComputer or cpa.name like :nameCompany order by ";
	private static final String SHOWORDERBY = "FROM Computer ORDER BY ";
	private static final String COUNTCOMPUTER = "SELECT COUNT(computer) FROM Computer computer";
	private static final String COUNTSEARCHCOMPUTER = "SELECT COUNT(computer) FROM Computer computer WHERE computer.name LIKE :nameComputer OR computer.company.name LIKE :nameCompany";
	private static final String DELETEACOMPUTER = "DELETE FROM Computer WHERE id = :id";
	private static final String UPDATEACOMPUTER = "UPDATE Computer SET name = :name, introduced = :introduced, discontinued = :discontinued, company_id = :companyId WHERE id = :id";
	private static final String COUNTCOMPUTERFROMCOMPANY = "SELECT COUNT(computer) FROM Computer computer WHERE computer.company.id = :companyId";
	
	private SessionFactory sessionFactory;
	
	@Autowired
	public ComputerDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public List<Computer> getList() {
        Session session = sessionFactory.openSession();
        List<Computer> list = session.createQuery(LISTCOMPUTER, Computer.class)
        		.getResultList();
        session.close();
		return list;
	}
	
	/**
	 * Used to show the details of computer(s) using their name as key
	 * 
	 * @param name used as a key to search in Computer database
	 * @return a list filled with the Computer object found with the key
	 */
	public List<Computer> getDetailsByName(String name) {
		Session session = sessionFactory.openSession();
		List<Computer> list = session.createQuery(SHOWCOMPUTERDETAILS, Computer.class)
				.setParameter("name", name)
				.getResultList();
		session.close();
		return list;
	}
	
	/**
	 * Used to show the details of computer(s) using their ID as key
	 * 
	 * @param ID used as a key to search in Computer database
	 * @return a list filled with the Computer object found with the key
	 */
	public Optional<Computer> getDetailsByID(long id) {
		Session session = sessionFactory.openSession();
		Computer computer = session.createQuery(SHOWCOMPUTERDETAILSBYID, Computer.class)
				.setParameter("id", id)
				.uniqueResult();
		session.close();
		return Optional.ofNullable(computer);
	}
	
	public List<Computer> getListOrderBy(OrderByComputer column, OrderByMode mode, Page page) {
		String order = SHOWORDERBY + column + " " + mode;
		Session session = sessionFactory.openSession();
		List<Computer> list = session.createQuery(order, Computer.class)
				.setFirstResult(page.getLimit())
				.setMaxResults(page.getOffset())
				.getResultList();
		session.close();
		return list;
	}

	public List<Computer> getComputerOrderByLike(OrderByComputer column, OrderByMode mode, String name, Page page) {
		String order = SEARCHCOMPUTERANDCOMPANY + "cpu."+column + " " + mode;
		Session session = sessionFactory.openSession();
		List<Computer> list = session.createQuery(order, Computer.class)
				.setParameter("nameComputer", '%' + name + '%')
				.setParameter("nameCompany", '%' + name + '%')
				.setFirstResult(page.getLimit())
				.setMaxResults(page.getOffset())
				.getResultList();
		session.close();
		return list;
	}
	
	public long countComputer() {
		long count = 0;
		Session session = sessionFactory.openSession();
		count = session.createQuery(COUNTCOMPUTER,Long.class)
				.getSingleResult();
		session.close();
		return count;
	}

	public long countComputerLike(String name) {
		long count = 0;
		Session session = sessionFactory.openSession();
		count = session.createQuery(COUNTSEARCHCOMPUTER,Long.class)
				.setParameter("nameComputer", '%' + name + '%')
				.setParameter("nameCompany", '%' + name + '%')
				.getSingleResult();
		session.close();
		return count;
	}

	public long countComputerFromCompanyId(long id) {
		long count = 0;
		Session session = sessionFactory.openSession();
		count = session.createQuery(COUNTCOMPUTERFROMCOMPANY,Long.class)
				.setParameter("companyId", id)
				.getSingleResult();
		session.close();
		return count;
	}

	public List<Computer> getListPage(Page page) {
		Session session = sessionFactory.openSession();
		List<Computer> list = session.createQuery(LISTCOMPUTER, Computer.class)
				.setFirstResult(page.getLimit())
				.setMaxResults(page.getOffset())
				.getResultList();
		session.close();
		return list;
	}
	
	/**
	 * Delete a computer using his ID
	 * 
	 * @param id the ID of the computer we want to delete
	 */
	public int delete(long id) {
		int numberOfDeletedElement = 0;
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			numberOfDeletedElement = session.createQuery(DELETEACOMPUTER)
					.setParameter("id", id)
					.executeUpdate();
			LOGGER.info(numberOfDeletedElement + " elements are now deleted");
			transaction.commit();
		} catch (Exception e) {
			LOGGER.info("ERROR DELETING COMPUTER",e);
			transaction.rollback();
		} finally {
			session.close();			
		}
		return numberOfDeletedElement;
	}
	
	/**
	 * Update a computer using his ID
	 * 
	 * @param computer contains the new configuration of computer
	 * @param id       the ID of the computer we want to change
	 */
	public long update(Computer computer) {
		int numberOfUpdatedElement = 0;
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			numberOfUpdatedElement = session.createQuery(UPDATEACOMPUTER)
					.setParameter("id", computer.getId())
					.setParameter("name", computer.getName())
					.setParameter("introduced", computer.getIntroduced())
					.setParameter("discontinued", computer.getDiscontinued())
					.setParameter("companyId", computer.getCompany() == null ? null : computer.getCompany().getId())
					.executeUpdate();				
			LOGGER.info(numberOfUpdatedElement + " elements with ID : " + computer.getId() + " are now updated");
			transaction.commit();			
		} catch (Exception e) {
			LOGGER.info("ERROR UPDATING COMPUTER",e);
			transaction.rollback();
		} finally {
			session.close();			
		}
		return numberOfUpdatedElement;
	}

	/**
	 * Insert a new computer in the database
	 * 
	 * @param computer object created with the parameters give by the user
	 */
	public long create(Computer computer) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		long numberOfComputerCreated = 0;
		try {
			session.save(computer);
			transaction.commit();
			numberOfComputerCreated = 1;
		} catch (Exception e) {
			LOGGER.info("ERROR CREATING COMPUTER",e);
			transaction.rollback();
			return 0;
		} finally {
			session.close();			
		}
		return numberOfComputerCreated;
	}
}
