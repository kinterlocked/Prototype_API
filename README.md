# Road-To-Api-Master

5/29 여정의 시작



## 06/02 Naver Clova OCR AI API 추가
### 프로젝트(폴더) 명 : demo
### 비고 

 * Clova_OCR.CLASS 참고, API KEY + SECRET KEY는 본인 것 사용하세요.
 * Naver Cloud Console 가입하고 naverOcr 들어가서 본인 템플릿 만들고 키 받으시면 됩니다. ( 템플릿 : 소스 이미지를 어떤 부분을 읽어들일건지 지정해주는 템플릿 )
 * Json parsing 할 때 해당 의존성 추가해야합니다. 소스코드 안에 주석처리 되어있음.



## 06/02 Email API 추가 (이규빈)
### 프로젝트(폴더) 명 : Email_API
### 비고

  * MailController는 "사용자"가 불편접수 이메일을 보내는 로직이고, 나머지 User 파일들은 비밀번호 찾기 과정에서 "서버"가 임시 비밀번호 이메일을 보내는 로직입니다. (소스코드 내 주석 참고)

  * application.yml에 SMTP 설정을 해둔 이메일 주소 및 비밀번호 등을 적어야 합니다. 이때 **개인정보 유출 방지를 위해 .gitignore 파일에 application.yml을 적는 것이 좋습니다.**
  
  * 작동 원리 및 SMTP 설정 방법 참고 : [Spring Boot SMTP 서버를 통한 이메일 인증기능 구현](https://velog.io/@dm911/Spring-Boot-SMTP-%EC%84%9C%EB%B2%84%EB%A5%BC-%ED%86%B5%ED%95%9C-%EC%9D%B4%EB%A9%94%EC%9D%BC-%EC%9D%B8%EC%A6%9D%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84)


## 06/02 PortOne 결제 API 추가
### 프로젝트(폴더) 명 : Portone_API
### 비고
 * 프로젝트 내 IMP번호는 발급받은 본인의 키를 사용해주세요.
 * jquery를 실행시키는 방식으로 구현하면 모든 결제 수단이 사용이 가능합니다.
 * https://developers.portone.io/docs/ko/readme?v=v1) 공식 문서 주소입니다.


<hr>

## 06/02 Spotify 음악 검색 API
### 프로젝트명 : Spotify_API
### 비고
**Spring boot, Vue.js 2가지로 각각 구현했으니, 편한 방법으로 사용하시면 됩니다.**
### 1. Spotify Back 폴더
 * pom.xml에 의존성 추가 해줘야 합니다. 최신버전 -> https://github.com/spotify-web-api-java/spotify-web-api-java  <br/>
```xml
<!-- SpotifyApi 라이브러리 -->
<dependency>
  <groupId>se.michaelthelin.spotify</groupId>
  <artifactId>spotify-web-api-java</artifactId>
  <version>8.4.0</version>
</dependency>  
<!-- jitpack 저장소 -->
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
  ```

 * spotify.client.id= <br/>
   spotify.client.secret= <br/>
   application.properties에 발급받은 키 작성 해 주세요!<br/>
   https://developer.spotify.com/
 * Spotify API는 전부 클라이언트의 토큰을 생성하고 인증해야 이용 가능합니다. 구현 해 놓음 

### 2. Spotify Front 폴더(06/12 추가)
- 적용된 라이브러리 : https://github.com/thelinmichael/spotify-web-api-node
- 코드 주석 참고하여 라이브러리 추가로 인스톨해야합니다.
- 참고 : https://developer.spotify.com/documentation/web-api/reference/get-recommendations
  
<hr>

## 06/03 Tmap 경로 찾기 API
### 프로젝트명 : Tmap_API
### 비고
* VITE_TMAP_API_KEY = <br/> 
  env 파일에 발급받은 키 작성 해 주세요!<br/>
  https://tmapapi.tmapmobility.com/main.html#webservice/sample/WebSamplePedestrian

<hr>

## 06/04 카카오로그인 API
### 프로젝트명 : KakaoLogin_API
### 비고
* 카카오 REST API 키 발급 및 작동원리 참고 : https://velog.io/@jiwoow00/Spring-boot-JWT-이용한-백엔드-카카오-로그인
* 프론트 LoginView.vue 에 리다이렉트 경로, Kakao REST API 키 작성해주세요.
* 백 application.properties 에 리다이렉트 경로, Kakao REST API 키 작성해주세요.
* 백 pom.xml 에 의존성 추가
```xml
		<!--JWT 의존성 3개 추가-->
		<!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-api -->
		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt-api</artifactId>
			<version>0.12.5</version>
		</dependency>
		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt-impl</artifactId>
			<version>0.12.5</version>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt-jackson</artifactId>
			<version>0.12.5</version>
			<scope>runtime</scope>
		</dependency>

		<!-- gson 의존성 추가 -->
		<!-- https://mvnrepository.com/artifact/com.google.code.gson/gson -->
		<dependency>
			<groupId>com.google.code.gson</groupId>
			<artifactId>gson</artifactId>
			<version>2.10.1</version>
		</dependency>
  ```

<hr>

## 06/04 네이버 검색광고 API (연관 키워드 추출)
### 프로젝트명 : AutoSearchAPI
### 비고
https://ads.naver.com 

1-1. 광고시스템에 로그인.

1-2. [도구 -> API 사용 관리] 메뉴 클릭

1-3. [네이버 검색광고 API 서비스 신청] 버튼 클릭

1-4. API키 자동 발급

1-5. https://cors-anywhere.herokuapp.com/corsdemo 접속 후 데모서버 접속 허용요청 필수!!(안 하면 403에러 발생)
기타 세부사항은 search.js 파일 주석 확인
검색 결과 데이터 확인용 index.html 파일 첨부

<hr>

## 06/05 reCAPTCHA API
### 프로젝트명 : reCAPTCHA API
### 사이트 : https://www.google.com/recaptcha/about/ 
[v3 Admin Console] 탭에 들어간 후 reCaptcha 유형에서 [테스트(v2)] 선택,
선택 후 ["로봇이 아닙니다." 체크박스] 선택

[도메인] 란에 "localhost" 추가 
	     
### 비고
 * pom.xml에 의존성 추가  <br/>
```xml
<!-- reCAPTCHA 라이브러리 -->
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
  ```

 * recaptcha.secret.key= <br/>
   recaptcha.site.key= <br/>
   application.properties에 발급받은 키 작성 필요<br/>

<hr>

## 06/05 GCP:자연어 처리
### 프로젝트명 : NaturalLanguage_API
### 비고
**비슷한 기능을 약간 다르게 2가지 방법으로 구현했으니, 원하는대로 조합해 사용하시면 됩니다.**

### 0. 공통
  - 입력한 자연어 텍스트를 기반으로 텍스트에 담긴 감정을 분석하고, 주요 entity의 타입과 중요도 분석 등을 수행합니다.
### 1. NaturalLanguage1 폴더
  - gradle 사용
  - JSON 시크릿 키 파일 사용해 인증 (보안에 유리)
  - POST 방식
  - syntax 분석 기능 추가
### 2. NaturalLanguage2 폴더
  - maven 사용
  - api key 사용해 인증 (사용이 편리함)
  - GET 방식
  - Entity 추출 후 언급 횟수, salience 내림차순 정렬
### 3. 참고
  - [구글 공식문서](https://cloud.google.com/natural-language/docs/basics?hl=ko)
  - [인증 설정 및 소스코드](https://jungwoon.github.io/google%20cloud/2017/11/13/BigQuery-Lecture-3.html)
  - [gradle 사용](https://velog.io/@greentea/Google-Cloud-Natural-Language-API-%EC%8A%A4%ED%94%84%EB%A7%81-%EB%B6%80%ED%8A%B8%EC%97%90%EC%84%9C-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0)

<hr>

## 06/11 coolsms API
### 프로젝트명 : coolsms_API
### 비고
**같은 기능을 2가지 구조로 구현했으니, 편한 방법으로 사용하시면 됩니다.**

### 0. 공통
- https://coolsms.co.kr/
- 요청에 포함된 수신번호에 문자를 송신합니다. (coolsms 최초 가입 시 300p 지급, 문자 1건당 20p 차감)
- pom.xml 의존성 추가 필요
 ```xml
		<dependency>
			<groupId>net.nurigo</groupId>
			<artifactId>sdk</artifactId>
			<version>4.3.0</version>
		</dependency>
 ```
- API 서비스 이용시 ,관리콘솔 로그인 필요
- https://console.coolsms.co.kr/
- 깃허브 예제 사이트
- https://github.com/coolsms/coolsms-java-examples

### 1. coolSms-test 폴더
- 컨트롤러 + 서비스 구조
- 쿼리스트링으로 수신번호 요청 (ex. http://localhost:8080/sms/send?to=수신번호)
- 랜덤 6자리 인증번호 송신
- application.properties 에 발급받은 키 작성<br/>
	coolsms.api.key = API 키<br/>
	coolsms.api.secret = API secret
- 참고 : https://velog.io/@dunkin00/Spring-Boot-문자-인증-구현하기-coolSMS
- 공식 예제(java) : https://developers.coolsms.co.kr/sdk-list/Java/send-message

### 2. coolsms
- 패키지 구조 : controller + service + provider
- provider에서, 보낼 문자의 내용 수정 가능
- application.properties 저장 정보
  api-key <br>
  api-secret-key <br>
  도메인의 경우, https://api.coolsms.co.kr로 고정됨<br>
  api-domain <br>
  from-number <br>
- 수신자 번호로 요청 (http://localhost:8080/test/send-sms/수신번호)

<hr>

## 06/12 API Gateway
### 프로젝트명 : API_Gateway
### 비고

### 1. API gateway의 개념
- 클라이언트와 백엔드 서비스 사이에 위치
- 역할
    1. 요청 라우팅: 클라이언트의 요청을 적절한 백엔드 서비스로 라우팅합니다.
    2. 인증 및 권한 부여: 요청이 백엔드 서비스에 도달하기 전에 인증 및 권한 부여를 처리합니다.
    3. 요청/응답 변환: 요청 및 응답을 변환하여 클라이언트와 서버 간의 호환성을 유지합니다.
    4. 트래픽 관리: 트래픽 관리, 제한, 모니터링 등의 기능을 제공합니다.
- 라우팅 주소 설정 방법 2가지
    1. WAS : 백엔드 개발자가 설계한 스프링부트 서버 등
    2. Serverless Computing Service : AWS Lambda, 네이버 Cloud Functions, Azure Functions 등

### 2. Serverless Computing Service (추후 학습 사항)
- 주로 API gateway와 함께 사용됨
- API gateway 뒤에 위치하며, 백엔드 로직을 구현
- 클라우드 서비스 제공자가 서버를 대신 관리해줌
- 장점 : 인프라 관리부담 감소, 서버 자원 자동확장, 비용 효율성
- 활용 사례 : 백엔드 API,  실시간 데이터 처리, IoT 등

### 3. 코드 설명
- 요청 흐름 : 'Client' 파일 -> API Gateway -> 'Server' 파일

  엔드포인트로 API Gateway의 Invoke URL을 설정하면, Gateway가 해당하는 API를 찾아 요청을 전송


- 응답 흐름 : 'Server' 파일 -> API Gateway -> 'Client' 파일

  API가 내부 로직을 수행한 후, 다시 Gateway를 거쳐 응답을 반환

### 4. 참고 링크
- [API Gateway를 활용한 API 개발과 비즈니스 로직 실행 (공식 유튜브)](https://www.youtube.com/watch?v=eXZpat-ByJQ)
- [API 가이드 (공식문서)](https://api.ncloud-docs.com/docs/ai-application-service-apigateway)

## 06/13 Naver ShortUrl
### 프로젝트명 : JavaShortUrl
### 비고

### ShortUrl 설명
- 원본 URL 주소를 네이버에 단축 URL API를 통해 단축된 url 주소를 만들 수 있다
- 만들어진 단축 url을 통해서도 사이트 이동이 가능하다
- 단축 url 뒤에 .qr을 붙히면 qr코드를 확인 할 수 있다.
- qr코드는 단축된 url을 통해서만 확인이 가능하다.

## 06/13 Naver Login

### Naver Login 
- 스크립트 태그를 통해 네이버 로그인 라이브러리 호출하여 네이버 로그인 기능 사용 하는 기능입니다
- 주석 통해서 설명 드리면서 같이 코드 확인하시면 될 것 같습니다.
