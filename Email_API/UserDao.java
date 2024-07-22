package com.ssafy.sponity.model.dao;

import org.apache.ibatis.annotations.Param;

import com.ssafy.sponity.model.dto.User;

public interface UserDao {
	
	User selectByUserId(String userId);
	
	void updatePassword(@Param("userId") String userId, @Param("tempPassword") String tempPassword);
	
}
