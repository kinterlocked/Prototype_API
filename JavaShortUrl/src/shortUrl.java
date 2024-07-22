// 네이버 검색 API 예제 - 단축 URL - GET
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class shortUrl {

    public static void main(String[] args) throws IOException{
    	
    	// https://developers.naver.com/main/
    	// 위 네이버 개발자 센터에 서비스쪽 API키로 등록해야 합니다.
    	// console.ncloud에 같은 이름의 API 서비스가 있던데 여기서 발급받은 키는 적용 X
    	
        String clientId = ""; // 개인 클라이언트 아이디값"
        String clientSecret = ""; // 개인 클라이언트 시크릿값"

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 단축 시킬 url 주소를 콘솔에 입력
        String originalURL = br.readLine();
        String apiURL = "https://openapi.naver.com/v1/util/shorturl?url=" + originalURL;

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL,requestHeaders);
        
        // 원본 url 입력 후 API 과정을 통해 단축된 url이 JSON 타입으로 반환됩니다.
        // https://me2.xxxxxxxx 형식의 url을 얻을 수 있고 
        // url 뒤에 .qr을 붙히면 qr코드를 얻을 수 있습니다.
        // ex) https://me2.xxxxxxxx.qr
        System.out.println(responseBody);

    }

    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}