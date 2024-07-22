
## 06/04 reCAPTCHA API
### 프로젝트명 : reCAPTCHA API
### 비고
```
npm install vue-recaptcha-v3
```
추가 설치 필요

### 주요 코드 (그 외의 코드는 기본 프로젝트 생성 시 만들어진 것)
#### BACKEND
* ReCaptchaApplication.java
* ReCaptcharController.java
* RecaptchaResponse.java
* RecaptchaService.java

#### FrontEnd
* FormComponent.vue

<hr>

### 코드의 흐름
##### 1. 클라이언트 사이드 코드 (FormComponent.vue):

    사용자가 폼을 제출하면, onSubmit 메서드가 호출됩니다.
    이때, Google reCAPTCHA에서 생성된 토큰을 서버로 전송합니다.

##### 2. 서버 사이드 코드 (RecaptchaController.java):

    클라이언트에서 전송된 토큰을 받기 위해 /recaptcha-verify 엔드포인트가 생성되어 있습니다.
    verifyRecaptcha 메서드는 전송된 토큰을 Google reCAPTCHA 서버에 전송하여 검증합니다.
    Google reCAPTCHA 서버로부터 받은 응답은 RecaptchaResponse 객체로 매핑되어 반환됩니다.

##### 3. Google reCAPTCHA 서비스 호출 (RecaptchaService.java):

    verifyRecaptcha 메서드에서는 전송된 토큰을 Google reCAPTCHA 서버에 전송하여 검증합니다.
    검증 결과를 RecaptchaResponse 객체로 매핑하고, 이를 반환합니다.

##### 4. 응답 처리 (RecaptchaController.java):

    verifyRecaptcha 메서드에서 반환된 응답은 RecaptchaController에서 처리됩니다.
    응답에 따라 클라이언트에게 success 또는 failure를 반환합니다.

<hr>
### 결과


<hr>

### 참고사항
* reCaptcha v1는 서비스 종료
* reCaptcha v2 종류
    * checkbox : 체크박스 클릭 시 로봇이라고 의심될 때만 그림 선택창이 나타남 (그 외의 경우에는 체크박스 체크만으로도 통과됨)
    * invisible : 사용자는 보이지 않는 reCAPTCHA를 통해 자동으로 인증. 이는 사용자에게 보이지 않는 캡차를 통해 신뢰성을 판단.
* reCaptcha v3 : 사용자는 페이지를 방문할 때마다 자동으로 인증되며, 보이지 않는 신뢰 점수를 기반으로 한다. 이 점수는 페이지 내에서 사용자의 활동을 평가하여 결정된다.

