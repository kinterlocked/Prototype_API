package com.example.coolsms.service;

import org.springframework.http.ResponseEntity;

public interface TestService {

	ResponseEntity<String>sendSms(String to);
	
}
