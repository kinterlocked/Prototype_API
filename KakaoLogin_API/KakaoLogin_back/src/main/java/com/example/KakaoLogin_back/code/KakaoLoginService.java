package com.example.KakaoLogin_back.code;

public interface KakaoLoginService {
	public String getKakaoAccessToken(String code);
	public User createKakaoUser(String token);
}
