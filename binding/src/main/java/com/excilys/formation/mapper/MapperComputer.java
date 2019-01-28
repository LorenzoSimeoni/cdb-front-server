/**
 * 
 */
package com.excilys.formation.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.excilys.formation.checker.Controller;
import com.excilys.formation.dto.ComputerDTO;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;

/**
 * @author excilys
 *
 */
@Component
public class MapperComputer {	
	/**
	 * Construct a computer object from a ResultSet (Select)
	 * @param results contains what we found in database 
	 * @return Computer object
	 * @throws SQLException
	 */
	public Computer mapper(ResultSet results) throws SQLException {
		Company company = new Company(results.getLong(5),results.getString(6));
		
		String introduced = results.getString(3);
		LocalDateTime introducedDateAndTime=null;
		if(introduced != null) {
			introduced = introduced.replace(' ', 'T');
			introducedDateAndTime = LocalDateTime.parse(introduced);
		}
		LocalDateTime discontinuedDateAndTime=null;
		String discontinued = results.getString(4);
		if( discontinued != null) {
			discontinued = discontinued.replace(' ', 'T');
			discontinuedDateAndTime = LocalDateTime.parse(discontinued);
		}
			
		Computer computer = new Computer();
		computer.setId(results.getInt(1));
		computer.setName(results.getString(2));
		computer.setIntroduced(introducedDateAndTime);
		computer.setDiscontinued(discontinuedDateAndTime);
		computer.setCompany(company);		
			
		return computer;
	}
	
	/**
	 * Construct a Computer object from arguments give by the user (update or user)
	 * @param name 
	 * @param introduced
	 * @param discontinued
	 * @param companyId
	 * @return Computer object
	 */
	public Computer mapper(long id, String name, String introduced, String discontinued, String companyId) {
		Computer computer;
		
		Company company = null;
		LocalDateTime localIntroduced = null;
		LocalDateTime localDiscontinued = null;
		if(introduced != null && Controller.testStringIsADate(introduced)) {
			localIntroduced = LocalDateTime.parse(introduced);
		}
		if(discontinued != null && Controller.testStringIsADate(discontinued)) {
			localDiscontinued = LocalDateTime.parse(discontinued);
		}
		if(companyId != null && Controller.testStringIsALong(companyId)) {
			long companyIdTransform = Long.parseLong(companyId);
			company = new Company(companyIdTransform);
		}
		
		computer = new Computer();
		computer.setId(id);
		computer.setName(name);
		computer.setIntroduced(localIntroduced);
		computer.setDiscontinued(localDiscontinued);
		computer.setCompany(company);			  
		return computer;
	}
	
	/**
	 * Construct a Computer object from arguments give by the user (update or user)
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param companyId
	 * @return
	 */
	public Computer mapper(String name, String introduced, String discontinued, String companyId) {
		Computer computer;
		
		Company company = null;
		LocalDateTime localIntroduced = null;
		LocalDateTime localDiscontinued = null;
		if(introduced != null && Controller.testStringIsADate(introduced)) {
			localIntroduced = LocalDateTime.parse(introduced);
		}
		if(discontinued != null && Controller.testStringIsADate(discontinued)) {
			localDiscontinued = LocalDateTime.parse(discontinued);
		}
		if(companyId != null && Controller.testStringIsALong(companyId)) {
			long companyIdTransform = Long.parseLong(companyId);
			company = new Company(companyIdTransform);
		}
		
		computer = new Computer();
		computer.setName(name);
		computer.setIntroduced(localIntroduced);
		computer.setDiscontinued(localDiscontinued);
		computer.setCompany(company);			  
		return computer;
	}
	
	public Computer mapper(ComputerDTO computerDTO) {
		
		if(computerDTO.getIntroduced() == null || computerDTO.getIntroduced().isEmpty()) {
			computerDTO.setIntroduced(null);
		} else {
			computerDTO.setIntroduced(computerDTO.getIntroduced().substring(0, 20));
		}
		if(computerDTO.getDiscontinued() == null || computerDTO.getDiscontinued().isEmpty()) {
			computerDTO.setDiscontinued(null);
		} else {
			computerDTO.setDiscontinued(computerDTO.getDiscontinued().substring(0, 20));
		}
		if(computerDTO.getCompanyId() == null || computerDTO.getCompanyId().isEmpty()) {
			computerDTO.setCompanyId(null);
		} 
		Computer computer = mapper(computerDTO.getId(), computerDTO.getName(), computerDTO.getIntroduced(), computerDTO.getDiscontinued(),computerDTO.getCompanyId());
		return computer;
	}
}
