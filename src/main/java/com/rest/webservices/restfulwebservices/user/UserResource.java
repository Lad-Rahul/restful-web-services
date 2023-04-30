package com.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
public class UserResource {

	private UserDaoService service;

	public UserResource(UserDaoService service) {
		this.service = service;
	}

	// GET /users
	@GetMapping("/users")
	public List<User> retriveAllUsers() {
		return service.findAllUsers();
	}

	/* //without HATEOAS
	
	// GET /users/{id}
	@GetMapping("/users/{id}")
	public User retriveUser(@PathVariable int id) {
		User user = service.findOne(id);
		
		if(user == null) {
			throw new UserNotFoundException("id : " + id);
		}
		
		return user;
	}
	
	*/
	
	//with HATEOAS - retriveUser
	//using EntitiyModal and WebMvcLinkBuilder
	// to return link for retrive all users in response
	@GetMapping("/users/{id}")
	public EntityModel<User> retriveUser(@PathVariable int id){
		User user = service.findOne(id);
		
		if(user == null) {
			throw new UserNotFoundException("id : " + id);
		}
		
		EntityModel<User> entityModel = EntityModel.of(user);
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retriveAllUsers());
		entityModel.add(link.withRel("all-users"));
		
		return entityModel;
	}
	

	// POST /users
	//@Valid for validation
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User newUser = service.save(user);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(newUser.getId())
				.toUri();
		
		
		return ResponseEntity.created(location).build();
	}
	
	//DELETE /usres/{id}
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		service.deleteById(id);
	}

	
	/* HATEOAS - Hypermedia As The Engine Of Application State
	 * In addition to data can we give links to customer to perform subsequent actions
	 * 
	 *  There are 2 implentation Options
	 *  1. Custom Formats and Implemenattion - Difficult to maintain
	 *  2. HAL (JSON Hypertext Application Language)
	 *     Simple format that gives a consistent and easy way to hyperlink between resources in API
	 */
}
