public class BaseAuthInfo {
    private String accessKey = "8D2B8C6C01AF11B8604B";  //"YOUR_ACCESS_KEY";
    private String accessSecret = "09282F8D3F063C08A66E1503604C71EE16CEEEDC"; //"YOUR_SECRET_KEY";
    private String apiKey = "";
    private String url = "";
    private String reqPath = "";

    public String getAccessKey() {
        return accessKey;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public String getReqPath() {
        return reqPath;
    }

    public String getUrl() {
        return url;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }

    public void setReqPath(String reqPath) {
        this.reqPath = reqPath;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
