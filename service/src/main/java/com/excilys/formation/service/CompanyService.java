package com.excilys.formation.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.dao.CompanyDAO;
import com.excilys.formation.dao.OrderByCompany;
import com.excilys.formation.dao.OrderByMode;
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
	public List<Company> showAll() {
		return companyDao.getList();
	}
	
	public Optional<Company> showDetailsById(long id) {
		return companyDao.getDetailsById(id);
	}
	
	public Optional<Company> showDetailsByName(String name) {
		return companyDao.getDetailsByName(name);
	}
	
	public List<Company> showPage(Page page) {
		return companyDao.getListPage(page);
	}

	public List<Company> getCompaniesOrderByLike(OrderByCompany column, OrderByMode mode, String name, Page page) {
		return companyDao.getCompanyOrderByLike(column, mode, name,page);
	}

	public List<Company> getCompaniesOrderBy(OrderByCompany column, OrderByMode mode, Page page) {
		return companyDao.getCompanyOrderBy(column, mode, page);
	}
	
	public long getCompanyCount() {
		return companyDao.getCountCompany();
	}
	
	public long getCompanyCountLike(String name) {
		return companyDao.countCompanyLike(name);
	}
	
	public int delete(long id) {
		return companyDao.delete(id);
	}
	
	public long update(Company company) {
		return companyDao.update(company);
	}
	
	public long create(Company company) {
		return companyDao.create(company);
	}
}
