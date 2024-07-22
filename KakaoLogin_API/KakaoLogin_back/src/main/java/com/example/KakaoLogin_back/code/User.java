package com.example.KakaoLogin_back.code;

import java.util.Date;

public class User {
	private int userId;
	private String email;
	private String password;
	private String birth;
	private String nickname;
	private int agreeTOS;
	private int agreePICU;
	private int agreePromotion;
	private String gender;
	private String role;
	private String createdAt;
	private String updatedAt;
	
	public User() {
		
	}
	
	public User(int userId, String email, String password, String birth, String nickname, int agreeTOS, int agreePICU,
			int agreePromotion, String gender, String role) {
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.birth = birth;
		this.nickname = nickname;
		this.agreeTOS = agreeTOS;
		this.agreePICU = agreePICU;
		this.agreePromotion = agreePromotion;
		this.gender = gender;
		this.role = role;
		this.createdAt = new Date().toString();
		this.updatedAt = new Date().toString();
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getAgreeTOS() {
		return agreeTOS;
	}
	public void setAgreeTOS(int agreeTOS) {
		this.agreeTOS = agreeTOS;
	}
	public int getAgreePICU() {
		return agreePICU;
	}
	public void setAgreePICU(int agreePICU) {
		this.agreePICU = agreePICU;
	}
	public int getAgreePromotion() {
		return agreePromotion;
	}
	public void setAgreePromotion(int agreePromotion) {
		this.agreePromotion = agreePromotion;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", email=" + email + ", password=" + password + ", birth=" + birth
				+ ", nickname=" + nickname + ", agreeTOS=" + agreeTOS + ", agreePICU=" + agreePICU + ", agreePromotion="
				+ agreePromotion + ", gender=" + gender + ", role=" + role + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + "]";
	}
	
	
}
