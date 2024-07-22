package com.example.coolsms.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.coolsms.provider.SmsProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
	
	private final SmsProvider smsProvider;

	@Override
	public ResponseEntity<String> sendSms(String to) {
		
		try {
			boolean result = smsProvider.sendSms(to);
			if (!result) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("메서지 전송중 예외가 발생");
			}
			
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("메서지 전송중 예외가 발생");
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("메서지 전송성공");
	}
}
