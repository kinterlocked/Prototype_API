import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Server {
    private final String accessKey;
    private final String accessSecret;
    private final String url;
    private final String reqPath;
    private final String apiKey;

    public Server(BaseAuthInfo baseAuthInfo) {
        this.accessKey = baseAuthInfo.getAccessKey();
        this.accessSecret = baseAuthInfo.getAccessSecret();
        this.url = baseAuthInfo.getUrl();
        this.reqPath = baseAuthInfo.getReqPath();
        this.apiKey = baseAuthInfo.getApiKey();
    }

    public HttpResponse<String> request() throws IOException, InterruptedException {
        String fullPath = url + reqPath;
        System.out.println(">> " + fullPath);

        String timestamp = getTimestamp();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullPath))
                .header("x-ncp-apigw-api-key", apiKey)
                .header("x-ncp-apigw-timestamp", timestamp)
                .header("x-ncp-iam-access-key", accessKey)
                .header("x-ncp-apigw-signature-v2", makeSignature(timestamp))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static String getTimestamp() {
        return String.valueOf(Instant.now().toEpochMilli());
    }

    private String makeSignature(String timestamp) {
        try {
            String message = "GET " + reqPath + "\n" + timestamp + "\n" + accessKey;
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec signingKey = new SecretKeySpec(accessSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(rawHmac);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to generate HMAC: " + e.getMessage());
        }
    }
}
