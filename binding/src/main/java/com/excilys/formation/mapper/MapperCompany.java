/**
 * 
 */
package com.excilys.formation.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.excilys.formation.model.Company;

/**
 * @author excilys
 *
 */

@Component
public class MapperCompany {
	
	/**
	 * Map a ResultSet in a Company object
	 * @param results contains what we found in database
	 * @return Constructed company object
	 * @throws SQLException
	 */
	public Company mapper(ResultSet results) throws SQLException {
		Company company = new Company(results.getLong(1),results.getString(2));
		
		return company;
	}

}
