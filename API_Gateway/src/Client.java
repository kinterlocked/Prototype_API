import java.io.IOException;
import java.net.http.HttpResponse;

public class Client {
    public static void main(String[] args) {
        BaseAuthInfo baseAuthInfo = new BaseAuthInfo();

        String url = "https://ojpkc8vx8i.apigw.ntruss.com"; // "YOUR_INVOKE_URL";
        String path = "/api_test_001/prod/test";  // "YOUR_INVOKE_PATH";

        baseAuthInfo.setUrl(url);
        baseAuthInfo.setReqPath(path);

        Server sender = new Server(baseAuthInfo);

        try {
            HttpResponse<String> response = sender.request();
            int rescode = response.statusCode();

            if (rescode == 200) {
                System.out.println("\n## result");
                System.out.println(response.body());
            } else {
                System.out.println("Error : " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
