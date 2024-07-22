package com.example.KakaoLogin_back.code;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@CrossOrigin
@RestController
@RequestMapping("/kakao-api")
public class KakaoController {
	private final KakaoLoginService kakaoLoginService;
//	private final UserService userService;
	private final JwtUtil jwtUtil;
	
	public KakaoController(KakaoLoginService kakaoLoginService, JwtUtil jwtUtil) {
		this.kakaoLoginService = kakaoLoginService;
//		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}
	
	@GetMapping("/kakao-login/{code}")
	public ResponseEntity<?> kakaoLogin(@PathVariable("code") String code, HttpSession session){
		//5. 프론트에서 받은 인가코드로 카카오 서버에 엑세스 토큰 요청
		String kakaoToken = kakaoLoginService.getKakaoAccessToken(code);
		//6. 엑세스토큰으로 사용자 정보 조회-> 응답 JSON 파싱해서 user 객체에 담기
		User user = kakaoLoginService.createKakaoUser(kakaoToken);
		Map<String,Object> result = new HashMap<>();
		
		//7,8. DB에서 유저 확인해서 로그인 처리 후 프론트에 응답 전송 (예시)
//		if(user != null) {
//			//DB에서 모든 유저 가져와서 일치하는 유저 정보로 로그인 하기
//			List<User> users = userService.selectAll();
//			for(User DBuser : users) {
//				if(DBuser.getEmail().equals(user.getEmail())) {
//					result.put("message", "성공");
//					result.put("access-token", jwtUtil.createToken(DBuser.getPassword()));
//					result.put("userId", DBuser.getUserId());
//					result.put("role", DBuser.getRole());
//					session.setAttribute("loginUser", DBuser);
//					
//					return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
//				}
//			}
//			int loginresult = userService.insertUser(user);
//			if(loginresult == 0) {
//				return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
//			}
//		}else {
//			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
//		}
		
		result.put("message", "성공");
		result.put("access-token", jwtUtil.createToken("testtest"));
		result.put("userId", "test-user-id");
		result.put("role", "test-user-role");
	
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
}
