package com.ssafy.sponity.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.sponity.model.dto.User;
import com.ssafy.sponity.model.service.UserService;

/*
 * 비밀번호를 찾기 위해 이메일로 임시 비밀번호를 보내는 로직입니다.
 * - 이메일 전송은 서비스 단에서 처리하고, DB는 임시 비밀번호로 갱신합니다.
 */
@RestController
public class UserController {
	
	// DI
	private final UserService userService;
	public UserController (UserService userService) {
		this.userService = userService;
	}
	
    
    // 비밀번호 재설정
    // - 이메일로 임시 비밀번호 전송
    @PostMapping("/reset-pw")
    public ResponseEntity<Integer> resetPassword(@RequestBody User user) {

    	int result = userService.resetPassword(user.getUserId(), user.getEmail());
    	
    	/*
    	 * 반환하는 숫자의 의미
    	 * 1: 회원가입 내역 없음
    	 * 2: 가입된 ID이지만, 이메일이 불일치
    	 * 3: 이메일로 임시 비밀번호 전송 성공
    	 * 4: 기타 서버 오류
    	 */
        switch(result) {
        case 1: return new ResponseEntity<>(1, HttpStatus.NOT_FOUND);
        case 2: return new ResponseEntity<>(2, HttpStatus.BAD_REQUEST);
        case 3: return new ResponseEntity<>(3, HttpStatus.OK);
        }
        
        return new ResponseEntity<>(4, HttpStatus.INTERNAL_SERVER_ERROR);
        
    }
}
