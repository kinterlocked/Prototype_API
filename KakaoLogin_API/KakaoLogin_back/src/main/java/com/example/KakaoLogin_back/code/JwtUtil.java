package com.example.KakaoLogin_back.code;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

//JWT 의존성 pom.xml에 추가하기
//<!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-api -->
//<dependency>
//	<groupId>io.jsonwebtoken</groupId>
//	<artifactId>jjwt-api</artifactId>
//	<version>0.12.5</version>
//</dependency>
//<dependency>
//	<groupId>io.jsonwebtoken</groupId>
//	<artifactId>jjwt-impl</artifactId>
//	<version>0.12.5</version>
//	<scope>runtime</scope>
//</dependency>
//<dependency>
//	<groupId>io.jsonwebtoken</groupId>
//	<artifactId>jjwt-jackson</artifactId>
//	<version>0.12.5</version>
//	<scope>runtime</scope>
//</dependency>

@Component
public class JwtUtil {
	private String key = "SSAFY_FINAL_PROJECT_FITMU_WITH_YUNMO";
	private SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
	
	// 다양한 데이터를 Map으로 받아서 처리를 할 수도 있지만,
	// 심플하게 ID만 받아서 토큰을 만들어보자~~
	public String createToken(String pw) {
		// 만료시간을 정해줘야되지만 없는걸로 하자.. 계속 사라지면 머리아프다
		return Jwts.builder().header().add("typ","JWT").and().claim("pw", pw).signWith(secretKey).compact();
	}
	
	
	// 실제로 확인하려고 하는 용도가 아니고 유효기간이 지났다면 알아서 에러를 발생시키려고 함.
	public Jws<Claims> validate(String token){
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
	}
}
