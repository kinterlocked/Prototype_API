package demo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Clova_OCR {
	public static void main(String[] args) throws Exception{
		//json 관련 라이브러리 의존성 추가 필수!
		/*
		 <dependency>
        <groupId>com.googlecode.json-simple</groupId>
        <artifactId>json-simple</artifactId>
        <version>1.1.1</version>
    </dependency>
			*/
		String apiURL = "your_api";
        String secretKey = "your_key";

        // 로컬 파일 경로 설정 (예시)
        String localImagePath = "C:\\MnJ\\Fitties\\vue-fitties\\src\\assets\\test.jpg";
        	
        
        	// http 요청
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("X-OCR-SECRET", secretKey);
            
            //요청 데이터를 JSON 방식으로 변환
            JSONObject json = new JSONObject();
            json.put("version", "V2");
            json.put("requestId", UUID.randomUUID().toString());
            json.put("timestamp", System.currentTimeMillis());
            JSONObject image = new JSONObject();
            image.put("format", "jpg");
            
            
            // 로컬 파일을 읽어 바이트 배열로 변환
            FileInputStream inputStream = new FileInputStream(localImagePath);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();

            // 바이트 배열을 Base64로 인코딩하여 JSON에 추가
            String encodedImage = Base64.getEncoder().encodeToString(buffer);
            image.put("data", encodedImage);
            image.put("name", "demo");

            JSONArray images = new JSONArray();
            images.add(image);
            json.put("images", images);
            String postParams = json.toString();

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());
            
            //여기서부터 json 파싱 -------------------------------------------------------------
            
            JSONParser parser = new JSONParser();
            // JSON 문자열을 JSONObject로 파싱
            JSONObject jsonResponse = (JSONObject) parser.parse(response.toString());

            // images 배열 추출
            JSONArray imagesArray = (JSONArray) jsonResponse.get("images");

            // 반환할 결과 리스트 생성
            List<String> resultList = new ArrayList<>();

            // images 배열 내부의 객체들을 순회하며 필요한 정보 추출
            for (Object imageObj : imagesArray) {
                JSONObject image1 = (JSONObject) imageObj;
                JSONArray fieldsArray = (JSONArray) image1.get("fields");

                // 필드 배열 내부의 객체들을 순회하며 필요한 정보 추출
                for (Object fieldObj : fieldsArray) {
                    JSONObject field = (JSONObject) fieldObj;

                    // 필요한 정보만을 추출하여 결과 리스트에 추가
                    String inferText = (String) field.get("inferText");
                    resultList.add(inferText);
                }
            }
            System.out.println(resultList.toString());
        // 클라이언트에 OCR 응답 반환
	}
}
