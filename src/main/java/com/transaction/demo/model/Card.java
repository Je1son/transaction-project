package com.transaction.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cards")
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "number_card")
	private String numberCard;
	
	@Column(name = "id_user")
	private Long idUser;
	
	@Column(name = "short_name")
	private String shortNumber;
	
	public Card() {
		
	}
	
	public Card(String name, String numberCard, Long idUser, String shortNumber) {
		this.name = name;
		this.numberCard = numberCard;
		this.idUser = idUser;
		this.shortNumber = shortNumber;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumberCard() {
		return numberCard;
	}
	public void setNumberCard(String numberCard) {
		this.numberCard = numberCard;
	}

	public Long getUser() {
		return idUser;
	}

	public void setUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getShortNumber() {
		return shortNumber;
	}

	public void setShortNumber(String shortNumber) {
		this.shortNumber = shortNumber;
	}
	
	
	
}
