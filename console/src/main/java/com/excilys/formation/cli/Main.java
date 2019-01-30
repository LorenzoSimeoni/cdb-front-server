/**
 * 
 */
package com.excilys.formation.cli;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author excilys
 *
 */

public class Main {
	/**
	 * @param args
	 */
	static int aStatistic = 1;
	public static void main(String []args) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		System.out.println(passwordEncoder.encode("password"));
//		FeatureCLI aCli = new FeatureCLI();
//		aCli.features();		
	}
}

