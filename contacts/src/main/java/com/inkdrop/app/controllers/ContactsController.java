package com.inkdrop.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inkdrop.app.repositories.ContactsRepository;
import com.inkdrop.models.Contact;

@RestController
@EnableAutoConfiguration
public class ContactsController {

	@Autowired
	ContactsRepository repository;

	@RequestMapping(method=RequestMethod.POST, path="/contacts/new")
	public ResponseEntity<String> saveUser(@RequestBody String user) throws Exception {
		try{
		ObjectMapper objectMapper = new ObjectMapper();
		Contact contact = objectMapper.readValue(user, Contact.class);
		contact = repository.save(contact);
		return new ResponseEntity<String>(objectMapper.writeValueAsString(contact), HttpStatus.CREATED);
		} catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(method=RequestMethod.GET, path="/contacts")
	public ResponseEntity<String> getUsers() throws Exception {
		String users = mapper().writeValueAsString(repository.findAll());
		return new ResponseEntity<String>(users, HttpStatus.OK);
	}

	private ObjectMapper mapper(){
		return new ObjectMapper();
	}
}
