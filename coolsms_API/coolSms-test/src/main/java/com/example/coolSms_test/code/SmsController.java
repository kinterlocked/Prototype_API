package com.example.coolSms_test.code;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
public class SmsController {
	private final SmsService smsService;
	
	public SmsController(SmsService smsService) {
		this.smsService = smsService;
	}
	
	
	@GetMapping("/send")
	public ResponseEntity<?> sendSms(@RequestParam(value="to") String to) {
		
		smsService.sendOne(to);
		
		return new ResponseEntity<String>("SMS 전송 성공",HttpStatus.OK);
	}
}
