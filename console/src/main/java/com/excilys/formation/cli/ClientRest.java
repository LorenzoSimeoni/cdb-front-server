package com.excilys.formation.cli;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.excilys.formation.dto.ComputerDTO;
import com.excilys.formation.model.Computer;

@Component
public class ClientRest {
 
	private static final String GETONECOMPUTER = "http://localhost:8080/webapp/Computer-rest/";
	private static final String GETALL = "http://localhost:8080/webapp/Computer-rest";
	private static final String CREATECOMPUTER = "http://localhost:8080/webapp/Computer-rest/create-rest";
	private static final String UPDATECOMPUTER = "http://localhost:8080/webapp/Computer-rest/update-rest/";
	private static final String DELETECOMPUTER = "http://localhost:8080/webapp/Computer-rest/";
	

	public Computer getComputer(long id) {
		Client client = ClientBuilder.newClient().register(new Authenticator("Lorenzo", "mdp"));
		return client
				.target(GETONECOMPUTER+id)
				.request(MediaType.APPLICATION_JSON)
				.get(Computer.class);
	}
	
	public List<Computer> getAllEmployee() {
		Client client = ClientBuilder.newClient().register(new Authenticator("Lorenzo", "mdp"));
		return client
				.target(GETALL)
				.request(MediaType.APPLICATION_JSON)
				.get(List.class);
	}
	
	public Response createComputer(ComputerDTO computerDTO) {
		Client client = ClientBuilder.newClient().register(new Authenticator("Lorenzo", "mdp"));
	    return client
	      .target(CREATECOMPUTER)
	      .request(MediaType.APPLICATION_JSON)
	      .post(Entity.entity(computerDTO, MediaType.APPLICATION_JSON));
	}
	
	public Response updateComputer(ComputerDTO computerDTO) {
		Client client = ClientBuilder.newClient().register(new Authenticator("Lorenzo", "mdp"));
	    return client
	      .target(UPDATECOMPUTER+computerDTO.getId())
	      .request(MediaType.APPLICATION_JSON)
	      .put(Entity.entity(computerDTO, MediaType.APPLICATION_JSON));
	}
	
	public Response deleteComputer(long id) {
		Client client = ClientBuilder.newClient().register(new Authenticator("Lorenzo", "mdp"));
	    return client
	      .target(DELETECOMPUTER+id)
	      .request(MediaType.APPLICATION_JSON)
	      .delete();
	}
}