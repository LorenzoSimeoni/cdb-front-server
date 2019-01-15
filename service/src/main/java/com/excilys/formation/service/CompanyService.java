package com.excilys.formation.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.dao.CompanyDAO;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Page;

@Service
public class CompanyService {
	
	private CompanyDAO companyDao;
	
	@Autowired
	public CompanyService(CompanyDAO companyDao) {
		this.companyDao = companyDao;
	}
	
	/**
	 * Fill a list with all the Company object found in the List<Company> gived by our companyDao
	 */
	public List<Company> show() {
		return companyDao.getList();
	}
	
	public Optional<Company> showDetailsById(long id) {
		return companyDao.getDetailsById(id);
	}
	
	public List<Company> showPage(Page page) {
		return companyDao.getListPage(page);
	}
	
	public int delete(long id) {
		return companyDao.delete(id);
	}
}
