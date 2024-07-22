package com.ssafy.sponity.model.service;

import com.ssafy.sponity.model.dto.User;

public interface UserService {
	
	int resetPassword(String userId, String email);
	
}
