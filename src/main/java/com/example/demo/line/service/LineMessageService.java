package com.example.demo.line.service;

import org.springframework.http.ResponseEntity;

import com.example.demo.line.vo.in.LineMessageIn;

public interface LineMessageService {
	
	ResponseEntity<String> processLineMessage(LineMessageIn lineMessageIn, String channelToken);
}
