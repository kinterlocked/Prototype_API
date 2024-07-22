package com.ssafy.sponity.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.sponity.model.dao.UserDao;
import com.ssafy.sponity.model.dto.User;

@Service
public class UserServiceImpl implements UserService {
	
	// DI
	private final UserDao userDao;
	public UserServiceImpl (UserDao userDao) {
		this.userDao = userDao;
	}
	
	// 비밀번호 암호화에 사용하는 객체
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	// 이메일 설정
	@Autowired
	private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "sponity@naver.com";
	

	// 비밀번호 재설정
	@Override
	public int resetPassword(String userId, String email) {
		
		// 가입된 회원인지 조회
		User user = userDao.selectByUserId(userId);
		if(user == null) {
			return 1;
		}
		
		// 이메일 일치여부 확인
		if(!email.equals(user.getEmail())) {
			return 2;
		}
		
		// 임시 비밀번호 생성
		String tempPassword = "";

		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
				'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        for (int i = 0; i < 8; i++) { // charSet 배열에서 무작위로 문자를 뽑아 임시 비밀번호를 생성합니다.
            int idx = (int) (charSet.length * Math.random()); 
            tempPassword += charSet[idx];
        }
		
        // 임시 비밀번호 암호화 및 DB 갱신
        // - 주의 : "암호화 된" 비밀번호로 갱신해야 합니다.
        String encryptedPassword = bCryptPasswordEncoder.encode(tempPassword);
		userDao.updatePassword(userId, encryptedPassword);
		
		// 임시 비밀번호를 이메일로 전송
		// - 주의 : "암호화되지 않은" 비밀번호를 전송해야 합니다.
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM_ADDRESS);
        message.setTo(email);
        message.setSubject("[SPONITY] " + userId + "님의 임시 비밀번호 안내 이메일입니다.");
        message.setText("안녕하세요. SPONITY입니다.\n" + user.getUserName() + "님의 임시 비밀번호는 " + tempPassword + "입니다.\n" + "로그인 후 즉시 비밀번호를 변경해주세요.");

        mailSender.send(message);
        
		return 3;
	}
	
}
