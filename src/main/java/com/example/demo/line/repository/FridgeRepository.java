package com.example.demo.line.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.line.entity.FridgeEntity;

@Repository
public interface FridgeRepository extends MongoRepository<FridgeEntity, String>{
	
	List<FridgeEntity> findByItemName(String itemName);
	
//	List<FridgeEntity> findByLineUserId(String lineUserId);
	
	void deleteFridgeEntityById(String id);
	
	FridgeEntity findTopByItemNameOrderByExpirationDateStrAsc(String itemName);
	
	Long deleteFirstByItemNameOrderByExpirationDateStrAsc(String itemName);
}
