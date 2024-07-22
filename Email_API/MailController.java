package com.ssafy.sponity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

/*
 * 사용자로부터 불편접수 이메일을 받는 로직입니다.
 * - 사용자가 직접 SMTP 서버를 이용할 수는 없으므로, 보내는 이메일 주소는 관리자 것으로 설정합니다.
 * - DB에 저장할 필요 없으므로, 해당 컨트롤러에서 로직을 전부 처리합니다.
 */
@RestController
@RequestMapping("/mail")
public class MailController {
	
	// 이메일 설정
	@Autowired
	private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = /* SMTP 설정을 해 둔 이메일 주소를 작성 */;
    
    // 이메일 전송
    @PostMapping
    public void claimMail(@RequestBody MailDTO mailDTO) {
    	SimpleMailMessage claimMail = new SimpleMailMessage();
    	
    	claimMail.setFrom(FROM_ADDRESS); // 보내는 이메일 주소 (관리자)
    	claimMail.setTo("abc@gmail.com"); // 받는 이메일 주소 (불편접수 담당자)
    	claimMail.setSubject(mailDTO.getName() + " 회원님으로부터 접수된 불편접수 이메일입니다."); // 이메일 제목
    	claimMail.setText(mailDTO.getMessage() + "\n\n회신 이메일 주소: " + mailDTO.getEmail()); // 이메일 내용
    	
    	mailSender.send(claimMail);
    }
    
    
    @Data
    static class MailDTO { // 사용자가 입력한 데이터를 담은 DTO
    	String name;
    	String email;
    	String message;
    }
}
