package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
public class LanguageController {

    // application.properties 파일에서 google.api.key 값을 읽어옴
    // json파일을 어디서 저장하는지 모르겠어서 일단 저는 key를 설정파일에 넣어놨습니다.
    // 보안상 추천하는 방법은 아니나 테스트겸 일단 했으므로 나중에 json파일로 저장해 관리하시길바랍니다.
    // 의존성 추가는 pom.xml에 3가지 추가하고 표시해뒀습니다.
    // http://localhost:8080/analyze?text=이거 설정 진짜 어렵다.
    // 스프링 부트 실행 후 주소창에 위 주석처럼 입력하면 나옵니다.

    // 저는 API키를 발급받았는데 이 경우 이렇게 하는 것이 맞습니다.
    // 다만 GCP에서 서비스 계정을 통해 발급 받은 경우 JSON파일을 발급받은 후 로컬에 저장해서 경로를 적어주시면 됩니다.
    @Value("${google.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    // /analyze 엔드포인트로 GET 요청을 처리
    @GetMapping("/analyze")
    public String analyzeSentiment(@RequestParam String text) {
        // 감정 분석을 위한 URL
        String sentimentUrl = "https://language.googleapis.com/v1/documents:analyzeSentiment?key=" + apiKey;
        // 엔터티 인식을 위한 URL
        String entitiesUrl = "https://language.googleapis.com/v1/documents:analyzeEntities?key=" + apiKey;

        // 분석할 문서 구성
        Map<String, Object> document = new HashMap<>();
        document.put("type", "PLAIN_TEXT");
        document.put("content", text);

        // 요청 본문 구성
        Map<String, Object> request = new HashMap<>();
        request.put("document", document);
        request.put("encodingType", "UTF8");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        try {
            // 감정 분석 요청
            ResponseEntity<Map> sentimentResponse = restTemplate.exchange(sentimentUrl, HttpMethod.POST, entity, Map.class);
            Map<String, Object> sentimentBody = sentimentResponse.getBody();
            String sentimentResult = "No sentiment data found.";
            if (sentimentBody != null && sentimentBody.containsKey("documentSentiment")) {
                Map<String, Object> sentiment = (Map<String, Object>) sentimentBody.get("documentSentiment");
                // 감정 점수
                // -1(부정) ~ 1(긍정)
                // 점수가 0이 나온는 경우 타입이 틀려 json파싱과정에서 오류로 인해 Number 수정
                Number score = (Number) sentiment.get("score");
                // 감정 크기
                Number magnitude = (Number) sentiment.get("magnitude");
                sentimentResult = String.format("Sentiment Score: %.2f<br><br>Sentiment Magnitude: %.2f", score.doubleValue(), magnitude.doubleValue());
            }

            // 엔터티 인식 요청
            ResponseEntity<Map> entitiesResponse = restTemplate.exchange(entitiesUrl, HttpMethod.POST, entity, Map.class);
            Map<String, Object> entitiesBody = entitiesResponse.getBody();
            StringBuilder entitiesResult = new StringBuilder("Entities:<br>");
            if (entitiesBody != null && entitiesBody.containsKey("entities")) {
                List<Map<String, Object>> entities = (List<Map<String, Object>>) entitiesBody.get("entities");
                // 고유한 엔터티와 그 빈도 및 salience를 저장할 Map
                // salience는 Entity가 문장에서 가지는 중요도를 의미합니다.
                Map<String, EntityInfo> uniqueEntities = new HashMap<>();
                for (Map<String, Object> entityInfo : entities) {
                    String name = (String) entityInfo.get("name");
                    String type = (String) entityInfo.get("type");
                    Number salience = (Number) entityInfo.get("salience");
                    EntityInfo entity2 = uniqueEntities.getOrDefault(name, new EntityInfo(type, 0, 0.0));
                    entity2.incrementCount();
                    entity2.addSalience(salience.doubleValue());
                    uniqueEntities.put(name, entity2);
                }
                // Map을 List로 변환하여 salience 내림차순 정렬
                List<Map.Entry<String, EntityInfo>> sortedEntities = new ArrayList<>(uniqueEntities.entrySet());
                sortedEntities.sort((e1, e2) -> Double.compare(e2.getValue().getSalience(), e1.getValue().getSalience()));

                // 정렬된 리스트를 사용하여 결과 문자열을 구성
                for (Map.Entry<String, EntityInfo> entry : sortedEntities) {
                    String name = entry.getKey();
                    EntityInfo entityInfo = entry.getValue();
                    entitiesResult.append(String.format("%s (%s) - Count: %d, Salience: %.2f<br>",
                            name, entityInfo.getType(), entityInfo.getCount(), entityInfo.getSalience()));
                }
            } else {
                entitiesResult.append("No entities found.");
            }

            // 결과를 포맷팅하여 반환
            return String.format("Text: %s<br><br>%s<br><br>%s", text, sentimentResult, entitiesResult.toString());
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // Entity 정보를 저장할 클래스
    static class EntityInfo {
        private final String type;
        private int count;
        private double totalSalience;

        public EntityInfo(String type, int count, double totalSalience) {
            this.type = type;
            this.count = count;
            this.totalSalience = totalSalience;
        }

        public String getType() {
            return type;
        }

        public int getCount() {
            return count;
        }

        public void incrementCount() {
            this.count++;
        }

        public double getSalience() {
            return totalSalience;
        }

        public void addSalience(double salience) {
            this.totalSalience += salience;
        }
    }
}
