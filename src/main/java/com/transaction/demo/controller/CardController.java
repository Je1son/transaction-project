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
import com.transaction.demo.model.Card;
import com.transaction.demo.model.User;
import com.transaction.demo.repository.CardRepository;
import com.transaction.demo.repository.UserRepository;

import com.transaction.demo.EncryptMethod;

//Indiciamos que es un controlador rest
@RestController
@RequestMapping("/api/v1/") // La raiz de la url, es decir http://localhost:8080/api/v1/
public class CardController {

	// Inyectamos el servicio de Card para su uso
	@Autowired
	private CardRepository cardRepository;

	// Inyectamos el servicio de User para su uso
	@Autowired
	private UserRepository userRepository;

	// Metodo para listar todas las tarjetas de un usuario mediante el metodo GET
	// como se muestra (http://localhost:8080/api/v1/user/id*/card)
	@GetMapping("/user/{id}/card")
	public List<Card> getAllCards(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {

		// Validamos si existe un usuario con el id enviado
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("" + id));

		// Retorna el listado de tarjetas de x usuario
		return this.cardRepository.findCardsByUserId(id);
	}

	// Metodo para crear una nueva tarjeta de un usuario mediante el metodo POST
	// como se muestra (http://localhost:8080/api/v1/user/id*/card)
	@PostMapping("/user/{id}/card")
	public Card createCard(@PathVariable(value = "id") Long id, @RequestBody Card card) throws Exception {

		// Validamos si existe un usuario con el id enviado
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("" + id));

		// Se instancia la clase que nos ayudara a encriptar el numero de la tarjeta
		// agregada por el usuario
		EncryptMethod aes = new EncryptMethod();
		aes.init();

		// Agregamos los datos restantes para el objeto Tarjeta
		card.setUser(user.getId());
		card.setShortNumber(card.getNumberCard().substring(0, 4));
		card.setNumberCard(aes.encrypt(card.getNumberCard()));

		// Indicamos la creacion de la nueva tarjeta
		return this.cardRepository.save(card);
	}

	// Metodo para actualizar una tarjeta de un usuario mediante el metodo PUT
	// como se muestra (http://localhost:8080/api/v1/user/idUser*/card/idCard*)
	@PutMapping("/user/{id_user}/card/{id}")
	public ResponseEntity<Card> updateCard(@PathVariable(value = "id_user") Long id_user,
			@PathVariable(value = "id") Long id, @Valid @RequestBody Card cardDetails) throws Exception {

		// Validamos si existe una tarjeta con el id enviado
		Card card = cardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("" + id));

		// Se instancia la clase que nos ayudara a encriptar el numero de la tarjeta
		// agregada por el usuario
		EncryptMethod aes = new EncryptMethod();
		aes.init();

		// Agregamos los datos restantes para el objeto Tarjeta
		card.setName(cardDetails.getName());
		card.setShortNumber(card.getNumberCard().substring(0, 4));
		card.setNumberCard(aes.encrypt(card.getNumberCard()));
		card.setUser(id);

		// Indicamos la actualizacion de la tarjeta
		return ResponseEntity.ok(this.cardRepository.save(card));

	}

	// Metodo para eliminar una tarjeta de un usuario mediante el metodo DELETE
	// como se muestra (http://localhost:8080/api/v1/card/id*
	@DeleteMapping("/card/{id}")
	public Map<String, Boolean> deleteCard(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		
		// Validamos si existe una tarjeta con el id enviado
		Card Card = cardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("" + id));
		
		// Indicamos la eliminacion de la tarjeta
		this.cardRepository.delete(Card);

		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		//Retornamos el resultado de dicha eliminacion
		return response;

	}

}
