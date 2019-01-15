/**
 * 
 */
package com.excilys.formation.cli;

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
//		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		System.out.println(passwordEncoder.encode("mdp"));
		FeatureCLI aCli = new FeatureCLI();
		aCli.features();		
	}
}

