package com.example.demo.web.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.web.entity.AppUserEntity;

@Repository
public interface AppUserRepository extends MongoRepository<AppUserEntity, String>{
	
	AppUserEntity findByAccount(String account);
	AppUserEntity findByLineUserId(String lineUserId);
}
