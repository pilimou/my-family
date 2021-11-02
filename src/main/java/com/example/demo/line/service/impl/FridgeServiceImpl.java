package com.example.demo.line.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.line.entity.FridgeEntity;
import com.example.demo.line.repository.FridgeRepository;
import com.example.demo.line.service.FridgeService;

@Service
public class FridgeServiceImpl implements FridgeService{
	
	@Autowired
	private FridgeRepository fridgeRepository;
	
	@Override
	public FridgeEntity insertFridge(FridgeEntity fridgeEntity) {
		return fridgeRepository.insert(fridgeEntity); 
	}

}
