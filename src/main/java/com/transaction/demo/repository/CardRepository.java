package com.transaction.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.transaction.demo.model.Card;

public interface CardRepository extends JpaRepository<Card, Long>{
	
	@Query(value = "SELECT * FROM cards WHERE id_user = :id", nativeQuery = true)
	List<Card> findCardsByUserId(Long id);

}
