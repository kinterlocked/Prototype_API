package com.example.demo.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.language.v1.AnalyzeEntitiesResponse;
import com.google.cloud.language.v1.AnalyzeSentimentResponse;
import com.google.cloud.language.v1.AnalyzeSyntaxResponse;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Entity;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.LanguageServiceSettings;
import com.google.cloud.language.v1.Sentiment;
import com.google.cloud.language.v1.Token;

@RestController
public class LanguageController {

    // 인증 키 파일 경로 설정
    String keyPath = "C:\\Users\\이규빈\\Desktop\\crucial-limiter-425408-m4-fc2c2d3b18b0.json";

    
    /*
     * # 기능 1 - Sentiment 분석
     * 
     * 응답 예시
     *   Sentiment magnitude: 0.800  // 감정의 규모 : 절대값으로 나타내며, 범위는 0 ~ +inf
     *   Sentiment score: -0.800     // 감정의 긍정적인 정도 : -1 (부정적) ~ 1 (긍정적)
     */
    @PostMapping("/sentiment")
    public String analyzeSentiment(@RequestBody String text) throws IOException {

        // 인증 키 파일을 사용하여 Credentials 객체 생성
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(keyPath));

        try (LanguageServiceClient language = LanguageServiceClient.create(LanguageServiceSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build())) {

            Document doc = Document.newBuilder().setContent(text).setType(Document.Type.PLAIN_TEXT).build();
            AnalyzeSentimentResponse response = language.analyzeSentiment(doc);
            Sentiment sentiment = response.getDocumentSentiment();
            
            if (sentiment == null) {
                return "No sentiment found";
            } else {
                return "Text: " + text +
                        "\nSentiment magnitude: " + sentiment.getMagnitude() +
                        "\nSentiment score: " + sentiment.getScore();
            }
        }
    }
    
    
    /*
     * # 기능 2 - Entity 분석
     * 
     * 응답 예시
     *   Name: 대한민국
     *   Type: ORGANIZATION
     *   Salience: 0.545509                                        // 텍스트 내에서 해당 entity가 얼마나 중요한지 나타내는 값. 0~1 사이 숫자로, 1에 가까울수록 중요합니다.
     *   Wikipedia URL: https://en.wikipedia.org/wiki/South_Korea  // 해당 키워드가 위키에 있으면 URL 주소를 반환해줍니다.
     */
    @PostMapping("/entity")
    public String analyzeEntity(@RequestBody String text) throws IOException {

        // 인증 키 파일을 사용하여 Credentials 객체 생성
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(keyPath));

        try (LanguageServiceClient language = LanguageServiceClient.create(LanguageServiceSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build())) {

            Document doc = Document.newBuilder().setContent(text).setType(Document.Type.PLAIN_TEXT).build();
            AnalyzeEntitiesResponse response = language.analyzeEntities(doc);
            
            // 엔터티 정보를 저장할 맵 생성
            Map<String, EntityInfo> entityMap = new HashMap<>();

            for (Entity entity : response.getEntitiesList()) {
                String entityName = entity.getName();
                EntityInfo entityInfo = entityMap.getOrDefault(entityName, new EntityInfo(entity.getType().toString(), 0.0, entity.getMetadataMap().get("wikipedia_url")));
                entityInfo.salience += entity.getSalience();
                entityMap.put(entityName, entityInfo);
            }

            // 결과 문자열 빌드
            // - 중요도(Salience)를 기준으로 내림차순 정렬
            List<Map.Entry<String, EntityInfo>> entityList = new ArrayList<>(entityMap.entrySet());
            entityList.sort(Comparator.comparingDouble(e -> -e.getValue().salience));

            StringBuilder result = new StringBuilder("Text: " + text + "\n\n-----< Entities >-----------------------\n\n");
            for (Map.Entry<String, EntityInfo> entry : entityList) {
                result.append("Name: " + entry.getKey() + "\n");
                result.append("Type: " + entry.getValue().type + "\n");
                result.append("Salience: " + entry.getValue().salience + "\n");
                result.append("Wikipedia URL: " + entry.getValue().wikipediaUrl + "\n\n");
            }

            return result.toString();
        }
    }
    
    
    /*
     * # 기능 3 - Syntax 분석
     * 
     * 응답 예시
     *   Token: running        // 분석 대상 텍스트에서 추출한 단어 토큰
     *   Part of Speech: VERB  // 단어의 품사
     *   Lemma: run            // 단어의 기본형 (다만 한국어의 경우 잘 안되는 듯)
     */
    @PostMapping("/syntax")
    public String analyzeSyntax(@RequestBody String text) throws IOException {

        // 인증 키 파일을 사용하여 Credentials 객체 생성
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(keyPath));

        try (LanguageServiceClient language = LanguageServiceClient.create(LanguageServiceSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build())) {

            Document doc = Document.newBuilder().setContent(text).setType(Document.Type.PLAIN_TEXT).build();
            AnalyzeSyntaxResponse response = language.analyzeSyntax(doc);
            
            StringBuilder result = new StringBuilder("Text: " + text + "\n\n-----< Syntax Tokens >-----------------------\n\n");
            for (Token token : response.getTokensList()) {
                result.append("Token: " + token.getText().getContent() + "\n");
                result.append("Part of Speech: " + token.getPartOfSpeech().getTag() + "\n");
                result.append("Lemma: " + token.getLemma() + "\n\n");
            }
            return result.toString();
        }
    }
    
    
    // 엔터티 정보를 저장하기 위한 클래스
    private static class EntityInfo {
        String type;
        double salience;
        String wikipediaUrl;

        EntityInfo(String type, double salience, String wikipediaUrl) {
            this.type = type;
            this.salience = salience;
            this.wikipediaUrl = wikipediaUrl;
        }
    }
}
