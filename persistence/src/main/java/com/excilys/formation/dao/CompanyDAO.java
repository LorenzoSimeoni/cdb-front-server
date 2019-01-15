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
	private static final String DELETEACOMPANY = "DELETE FROM Company WHERE id = :id";
	private static final String DELETECOMPUTERS = "DELETE FROM Computer WHERE company_id = :id";
    
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
	
	public Optional<Company> getDetailsById(long id) {
		Session session = sessionFactory.openSession();
		Company company = session.createQuery(LISTCOMPANYDETAILSBYID,Company.class)
				.setParameter("id", id)
				.getSingleResult();
		session.close();
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
}
