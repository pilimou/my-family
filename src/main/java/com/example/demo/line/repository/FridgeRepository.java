package com.example.demo.line.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import com.example.demo.line.entity.FridgeEntity;

@Repository
public interface FridgeRepository extends MongoRepository<FridgeEntity, String>{
	
	List<FridgeEntity> findByItemName(String itemName);
	
//	List<FridgeEntity> findByLineUserId(String lineUserId);
	
	void deleteFridgeEntityById(String id);
	
	FridgeEntity findTopByItemNameOrderByExpirationDateStrAsc(String itemName);
	
	Long deleteFirstByItemNameOrderByExpirationDateStrAsc(String itemName);
}
