/**
 * 
 */
package com.excilys.formation.mapper;

import org.springframework.stereotype.Component;

import com.excilys.formation.dto.CompanyDTO;
import com.excilys.formation.model.Company;

/**
 * @author excilys
 *
 */

@Component
public class MapperCompany {
	
	public Company mapper(CompanyDTO companyDTO) {
		Company company = new Company();
		company.setId(companyDTO.getId());
		company.setName(companyDTO.getName());			
		
		return company;
	}

}
