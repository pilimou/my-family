package com.example.demo.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.web.entity.AppUserEntity;
import com.example.demo.web.repository.AppUserRepository;
import com.example.demo.web.service.AppUserService;


@Service
public class AppUserServiceImpl implements AppUserService{
	
	@Autowired
	private AppUserRepository appUserRepository;

	@Override
	public AppUserEntity getAppUser(String account) {	
		return appUserRepository.findByAccount(account);
	}
	
	
}
