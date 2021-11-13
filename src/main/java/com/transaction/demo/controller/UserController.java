package com.transaction.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transaction.demo.exception.ResourceNotFoundException;
import com.transaction.demo.model.User;
import com.transaction.demo.repository.UserRepository;

@RestController
@RequestMapping("/api/v1/")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	//Listar usuarios
	@GetMapping("user")
	public List<User> getAllUsers() {
		return this.userRepository.findAll();
	}
	
	//Listar usuarios por id
	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUSerById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("" + id));
		return ResponseEntity.ok().body(user);
	}
	
	//Crear usuarios
	@PostMapping("user")
	public User createUser(@RequestBody User user) {
		return this.userRepository.save(user);
	}
	
	//Actualziar usuarios
	@PutMapping("/user/{id}")
	public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id, @Valid @RequestBody User userDetails) throws ResourceNotFoundException{
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("" + id));
		
		user.setDocument(userDetails.getDocument());
		user.setFirstName(userDetails.getFirstName());
		user.setLastName(userDetails.getLastName());
		user.setEmail(userDetails.getEmail());
		
		return ResponseEntity.ok(this.userRepository.save(user));

	}
	
	//Eliminar usuarios
	@DeleteMapping("/user/{id}")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long id) throws ResourceNotFoundException{
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("" + id));
		this.userRepository.delete(user);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		
		return response;
		
	}
	
	
}
