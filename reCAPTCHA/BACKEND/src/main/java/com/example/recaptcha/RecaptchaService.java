package com.example.recaptcha;

import com.example.recaptcha.RecaptchaResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RecaptchaService {
    @Value("${recaptcha.secret.key}")
    private String secretKey;

    private final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public boolean verifyRecaptcha(String token) {
        RestTemplate restTemplate = new RestTemplate();
        String params = "?secret=" + secretKey + "&response=" + token;
        RecaptchaResponse recaptchaResponse = restTemplate.postForObject(RECAPTCHA_VERIFY_URL + params, null, RecaptchaResponse.class);
        return recaptchaResponse != null && recaptchaResponse.isSuccess();
    }
}