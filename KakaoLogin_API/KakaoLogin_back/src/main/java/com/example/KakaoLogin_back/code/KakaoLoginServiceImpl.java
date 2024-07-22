package com.example.KakaoLogin_back.code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

//gson 의존성 pom.xml 에 추가
//<!-- https://mvnrepository.com/artifact/com.google.code.gson/gson -->
//<dependency>
//    <groupId>com.google.code.gson</groupId>
//    <artifactId>gson</artifactId>
//    <version>2.10.1</version>
//</dependency>

@PropertySource("classpath:application.properties")
@Service
public class KakaoLoginServiceImpl implements KakaoLoginService {
	
	@Value("${kakao.key.client-id}")
	private String clientId;
	
	@Value("${kakao.redirect-uri}")
	private String redirectUri;
	
	
	//5. 프론트에서 받은 인가코드로 카카오 서버에 엑세스 토큰 요청
	public String getKakaoAccessToken(String code) {
		String access_Token = "";
		String refresh_Token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";

		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// POST 요청을 위해 기본값이 false인 setDoOutput을 true로
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			// POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=" + clientId ); // TODO REST_API_KEY 입력
			sb.append("&redirect_uri=" + redirectUri); // TODO 인가코드 받은 redirect_uri 입력
			sb.append("&code=" + code);
			bw.write(sb.toString());
			bw.flush();

			// 결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);

			// Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
			JsonElement element = JsonParser.parseString(result);

			access_Token = element.getAsJsonObject().get("access_token").getAsString();
			refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

			System.out.println("access_token : " + access_Token);
			System.out.println("refresh_token : " + refresh_Token);

			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return access_Token;
	}
	
	//6. 엑세스토큰으로 사용자 정보 조회-> 응답 JSON 파싱해서 user 객체에 담기
	public User createKakaoUser(String token) {

		String reqURL = "https://kapi.kakao.com/v2/user/me";

	    //access_token을 이용하여 사용자 정보 조회
	    try {
	       URL url = new URL(reqURL);
	       HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	       conn.setRequestMethod("POST");
	       conn.setDoOutput(true);
	       conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

	       //결과 코드가 200이라면 성공
	       int responseCode = conn.getResponseCode();
	       System.out.println("responseCode : " + responseCode);

	       //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
	       BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	       String line = "";
	       String result = "";

	       while ((line = br.readLine()) != null) {
	           result += line;
	       }
	       System.out.println("response body : " + result);

	       //Gson 라이브러리로 JSON파싱
	       JsonElement element = JsonParser.parseString(result);

	       int id = element.getAsJsonObject().get("id").getAsInt();
	       boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
	       String email = "";
	       if(hasEmail){
	           email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
	       }
	       String nickname = element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();

	       System.out.println("id : " + id);
	       System.out.println("email : " + email);
	       System.out.println("ninkname : " + nickname);
	       
	       
	       User user = new User();
	       user.setAgreePICU(1);
	       user.setAgreePromotion(1);
	       user.setAgreeTOS(1);
	       user.setNickname(nickname);
	       user.setEmail(email);
	       user.setPassword(token);
	       br.close();
	       
	       return user;

	       } catch (IOException e) {
	            e.printStackTrace();
	       }
	    return null;
	 }

}
