package com.excilys.formation.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.dao.ComputerDAO;
import com.excilys.formation.dao.OrderByComputer;
import com.excilys.formation.dao.OrderByMode;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.Page;

@Service
public class ComputerService {
	
	private ComputerDAO computerDao;
	
	@Autowired
	public ComputerService(ComputerDAO computerDao) {
		this.computerDao = computerDao;
	}
	
	public List<Computer> showAll() {
		return computerDao.getList();
	}
	
	public List<Computer> showComputerPage(Page page) {
		return computerDao.getListPage(page);
	}

	public List<Computer> showComputerDetails(String name) {
		return computerDao.getDetailsByName(name);
	}

	public List<Computer> getComputerOrderByLike(OrderByComputer column, OrderByMode mode,String name, Page page) {
		return computerDao.getComputerOrderByLike(column, mode, name,page);
	}
	
	public Optional<Computer> showComputerDetailsByID(long id) {
		return computerDao.getDetailsByID(id);
	}
	
	public List<Computer> getListOrderBy(OrderByComputer column, OrderByMode mode, Page page) {
		return computerDao.getListOrderBy(column, mode, page);
	}

	public int deleteComputer(long id) {
		return computerDao.delete(id);
	}
	
	public long createComputer(Computer computer) {
		return computerDao.create(computer);
	}
	
	public long updateComputer(Computer computer) {
		return computerDao.update(computer);
	}
	
	public long countComputer() {
		return computerDao.countComputer();
	}
	
	public long countComputerLike(String name) {
		return computerDao.countComputerLike(name);
	}
	
	public long countComputerFromCompanyId(long id) {
		return computerDao.countComputerFromCompanyId(id);
	}
}
