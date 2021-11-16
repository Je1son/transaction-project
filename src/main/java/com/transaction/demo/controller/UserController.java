package com.transaction.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

//Indiciamos que es un controlador rest
@RestController
@RequestMapping("/api/v1/") // La raiz de la url, es decir http://localhost:8080/api/v1/
public class UserController {

	// Inyectamos el servicio de User para su uso
	@Autowired
	private UserRepository userRepository;

	// Metodo para traer todo el listado de usuarios mediante el metodo GET
	// como se muestra (http://localhost:8080/api/v1/user)
	@GetMapping("user")
	public List<User> getAllUsers() {

		// Retorna el listado de usuarios
		return this.userRepository.findAll();
	}

	// Metodo para traer un usuario por id mediante el metodo GET
	// como se muestra (http://localhost:8080/api/v1/user/id*)
	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUSerById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {

		// Realizamos la consulta del usuario con el id enviado
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("" + id));

		// Retornamos el usuario
		return ResponseEntity.ok().body(user);
	}

	// Metodo para crear un nuevo usuario mediante el metodo POST
	// como se muestra (http://localhost:8080/api/v1/user)
	@PostMapping("user")
	public User createUser(@RequestBody User user) {

		// Indicamos la creacion del nuevo usuario
		return this.userRepository.save(user);
	}

	// Metodo para actualizar un usuario mediante el metodo PUT
	// como se muestra (http://localhost:8080/api/v1/user/id*)
	@PutMapping("/user/{id}")
	public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id, @Valid @RequestBody User userDetails)
			throws ResourceNotFoundException {

		// Validamos si existe un usuario con el id enviado
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("" + id));

		// Agregamos los datos restantes para el objeto Usuario
		user.setDocument(userDetails.getDocument());
		user.setFirstName(userDetails.getFirstName());
		user.setLastName(userDetails.getLastName());
		user.setEmail(userDetails.getEmail());

		// Indicamos la actualizacion del usuario
		return ResponseEntity.ok(this.userRepository.save(user));

	}

	// Metodo para eliminar un usuario mediante el metodo DELETE
	// como se muestra (http://localhost:8080/api/v1/user/id*
	@DeleteMapping("/user/{id}")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		
		// Validamos si existe un usuario con el id enviado
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("" + id));
		
		// Indicamos la eliminacion de la tarjeta
		this.userRepository.delete(user);

		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		
		//Retornamos el resultado de dicha eliminacion
		return response;

	}

}
