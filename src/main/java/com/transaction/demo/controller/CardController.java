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

@RestController
@RequestMapping("/api/v1/")
public class CardController {

	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	//Listar trajetas por usuario
		@GetMapping("/user/{id}/card")
		public List<Card> getAllCards(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
			
			User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("" + id));
			
			return this.cardRepository.findCardsByUserId(id);
		}
		
		//Crear tarjetas
		@PostMapping("/user/{id}/card")
		public Card createCard(@PathVariable(value = "id") Long id, @RequestBody Card card) throws ResourceNotFoundException {
			
			User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("" + id));
			
			card.setUser(user.getId());
			
			return this.cardRepository.save(card);
		}
		
		//Actualziar tarjetas
		@PutMapping("/user/{id_user}/card/{id}")
		public ResponseEntity<Card> updateCard(@PathVariable(value = "id_user") Long id_user, @PathVariable(value = "id") Long id, @Valid @RequestBody Card CardDetails) throws ResourceNotFoundException{
			
			User user = userRepository.findById(id_user).orElseThrow(() -> new ResourceNotFoundException("" + id_user));
			
			Card Card = cardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("" + id));
			
			Card.setName(CardDetails.getName());
			Card.setNumberCard(CardDetails.getNumberCard());
			Card.setUser(user.getId());
			
			return ResponseEntity.ok(this.cardRepository.save(Card));

		}
		
		//Eliminar usuarios
		@DeleteMapping("/card/{id}")
		public Map<String, Boolean> deleteCard(@PathVariable(value = "id") Long id) throws ResourceNotFoundException{
			Card Card = cardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("" + id));
			this.cardRepository.delete(Card);
			
			Map<String, Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
			
			return response;
			
		}
	
}
