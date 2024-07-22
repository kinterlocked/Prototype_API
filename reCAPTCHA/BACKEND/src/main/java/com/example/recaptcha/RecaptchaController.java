package com.example.recaptcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RecaptchaController {

    @Autowired
    private RecaptchaService recaptchaService;

    @PostMapping("/recaptcha-verify")
    public ResponseEntity<Map<String, Object>> verifyRecaptcha(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        boolean isValid = recaptchaService.verifyRecaptcha(token);
        Map<String, Object> response = new HashMap<>();
        response.put("success", isValid);
        return ResponseEntity.ok(response);
    }
}
