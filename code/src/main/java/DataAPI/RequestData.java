package DataAPI;

import Domain.Request;

public class RequestData {
    private String storeName;
    private String content;
    private String senderName;
    private String comment;
    private int id;

    public RequestData(String storeName, String content) {
        this.storeName = storeName;
        this.content = content;
        this.senderName=null;
        this.comment=null;
        this.id=-1;
    }

    public RequestData(Request r) {
        this.storeName = r.getStoreName();
        this.content = r.getContent();
        this.senderName=r.getSenderName();
        this.comment=r.getComment();
        this.id=r.getId();
    }

    public String getStoreName() {
        return storeName;
    }

    public String getContent() {
        return content;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getComment() {
        return comment;
    }

    public int getId() {
        return id;
    }
}
