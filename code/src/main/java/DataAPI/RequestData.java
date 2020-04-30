package DataAPI;

public class RequestData {
    private String storeName;
    private String content;

    public RequestData(String storeName, String content) {
        this.storeName = storeName;
        this.content = content;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
