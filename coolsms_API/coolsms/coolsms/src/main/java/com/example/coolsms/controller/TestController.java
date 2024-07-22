package com.example.coolsms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coolsms.service.TestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
	
	private final TestService testService;
	
	@GetMapping("/send-sms/{to}")
	public ResponseEntity<String> sendSms(@PathVariable("to") String to) {
		ResponseEntity<String> response = testService.sendSms(to);
		return response;
	}

}
