package Domain;

public class Request {

    private String senderName;
    private String storeName;
    private String content;
    private String comment;

    public Request(String senderName, String storeName, String content) {
        this.senderName = senderName;
        this.storeName=storeName;
        this.content = content;
        comment=null;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
