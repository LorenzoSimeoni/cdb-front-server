package com.excilys.formation.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.model.Company;
import com.excilys.formation.model.Page;

/**
 * 
 * @author excilys
 *
 */
@Repository
public class CompanyDAO {

	private final static Logger LOGGER = LogManager.getLogger(CompanyDAO.class.getName());
	private static final String LISTCOMPANY = "FROM Company";
	private static final String LISTCOMPANYDETAILSBYID = "FROM Company WHERE id = :id";
	private static final String LISTCOMPANYDETAILSBYNAME = "FROM Company WHERE name = :name";
	private static final String DELETEACOMPANY = "DELETE FROM Company WHERE id = :id";
	private static final String DELETECOMPUTERS = "DELETE FROM Computer WHERE company_id = :id";
	private static final String UPDATECOMPANY = "UPDATE Company SET name = :name WHERE id = :id";
	private static final String SEARCHCOMPANY = "FROM Company company WHERE company.name LIKE :nameCompany ORDER BY ";
	private static final String SHOWCOMPANIES = "FROM Company company ORDER BY ";
	private static final String COUNTCOMPANY = "SELECT COUNT(company) FROM Company company";

    
	private SessionFactory sessionFactory;
	
	@Autowired
	public CompanyDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void save(Company company) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(company);
		tx.commit();
		session.close();
	}

	public List<Company> getList() {
        Session session = sessionFactory.openSession();
        List<Company> list = session.createQuery(LISTCOMPANY,Company.class)
        		.getResultList();
        session.close();
		return list;
	}
	
	public long getCountCompany() {
		long count = 0;
		Session session = sessionFactory.openSession();
		count = session.createQuery(COUNTCOMPANY,Long.class)
				.getSingleResult();
		session.close();
		return count;
	}
	
	public Optional<Company> getDetailsById(long id) {
		Company company = null;
		try(Session session = sessionFactory.openSession();) {
			company = session.createQuery(LISTCOMPANYDETAILSBYID,Company.class)
					.setParameter("id", id)
					.getSingleResult();
			session.close();
		} catch (NoResultException e) {
			LOGGER.info("No Company Found");
		}
		
		return Optional.ofNullable(company);
	}
	
	public Optional<Company> getDetailsByName(String name) {
		Company company = null;
		try(Session session = sessionFactory.openSession();) {
			 company = session.createQuery(LISTCOMPANYDETAILSBYNAME,Company.class)
					.setParameter("name", name)
					.getSingleResult();
			session.close();			
		} catch (NoResultException e) {
			LOGGER.info("No Company Found");
		}
		return Optional.ofNullable(company);
	}

	public List<Company> getListPage(Page page) {
		Session session = sessionFactory.openSession();
		List<Company> list = session.createQuery(LISTCOMPANY,Company.class)
				.setFirstResult(page.getLimit())
				.setMaxResults(page.getOffset())
				.getResultList();
		session.close();
		return list;
	}
	
	public List<Company> getCompanyOrderByLike(OrderByCompany column, OrderByMode mode, String name, Page page) {
		String order = SEARCHCOMPANY + column + " " + mode;
		Session session = sessionFactory.openSession();
		List<Company> list = session.createQuery(order,Company.class)
				.setParameter("nameCompany", '%' + name + '%')
				.setFirstResult(page.getLimit())
				.setMaxResults(page.getOffset())
				.getResultList();
		session.close();
		return list;
	}
	
	public List<Company> getCompanyOrderBy(OrderByCompany column, OrderByMode mode, Page page) {
		String order = SHOWCOMPANIES + column + " " + mode;
		Session session = sessionFactory.openSession();
		List<Company> list = session.createQuery(order,Company.class)
				.setFirstResult(page.getLimit())
				.setMaxResults(page.getOffset())
				.getResultList();
		session.close();
		return list;
	}
	
	public int delete(long id) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		int numberOfDeletedElement = 0;
		
		try {
			numberOfDeletedElement = session.createQuery(DELETECOMPUTERS)
					.setParameter("id", id)
					.executeUpdate();
			LOGGER.info(numberOfDeletedElement + " elements are now deleted");
			numberOfDeletedElement = session.createQuery(DELETEACOMPANY)
					.setParameter("id", id)
					.executeUpdate();
			LOGGER.info(numberOfDeletedElement + " elements with ID : " + id + " are now deleted");
			transaction.commit();
		} catch (Exception e) {
			LOGGER.info("ERROR DELETING COMPANY",e);
			transaction.rollback();
		} finally {
			session.close();
		}
		return numberOfDeletedElement;
	}

	public long update(Company company) {
		int numberOfUpdatedElement = 0;
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			numberOfUpdatedElement = session.createQuery(UPDATECOMPANY)
					.setParameter("id", company.getId())
					.setParameter("name", company.getName())
					.executeUpdate();				
			LOGGER.info(numberOfUpdatedElement + " elements with ID : " + company.getId() + " are now updated");
			transaction.commit();			
		} catch (Exception e) {
			LOGGER.info("ERROR UPDATING COMPANY",e);
			transaction.rollback();
		} finally {
			session.close();			
		}
		return numberOfUpdatedElement;
	}

	public long create(Company company) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		long numberOfCompanyCreated = 0;
		try {
			session.save(company);
			transaction.commit();
			numberOfCompanyCreated=1;
		} catch (Exception e) {
			LOGGER.info("ERROR CREATING COMPANY",e);
			transaction.rollback();
		} finally {
			session.close();			
		}
		return numberOfCompanyCreated;
	}
}
