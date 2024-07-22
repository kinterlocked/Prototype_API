package com.example.coolsms.provider;

import javax.net.ssl.SSLEngineResult.Status;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Component
public class SmsProvider {
	
	// nurigo에서 제공 
	private final DefaultMessageService messageService;
	
	@Value("${sms.from-number}") 
	private String FROM;
	//생성자
	public SmsProvider(
			@Value("${sms.api-key}")String API_KEY,
			@Value("${sms.api-secret-key}") String API_SECRET_KEY,
			@Value("${sms.api-domain}") String DOMAIN
			) {
		this.messageService = NurigoApp.INSTANCE.initialize(API_KEY, API_SECRET_KEY, DOMAIN);
	}
	
	public boolean sendSms(String to) {
		Message message = new Message();
		message.setFrom(to);
		message.setTo(to);
		message.setText("문자내용");
		
		SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));
		
		String statusCode = response.getStatusCode();
		boolean result = statusCode.equals("2000");
		
		return result;				
	}
}
