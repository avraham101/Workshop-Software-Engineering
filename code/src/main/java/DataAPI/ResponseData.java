package DataAPI;

public class ResponseData {
    int requestId;
    String content;

    public ResponseData(int requestId, String content) {
        this.requestId = requestId;
        this.content = content;
    }

    public int getRequestId() {
        return requestId;
    }

    public String getContent() {
        return content;
    }


}
