package Domain;

import java.util.concurrent.atomic.AtomicReference;

public class Request {

    private String senderName;
    private String storeName;
    private String content;
    private AtomicReference<String> comment;
    private int id;

    public Request(String senderName, String storeName, String content,int id) {
        this.senderName = senderName;
        this.storeName=storeName;
        this.content = content;
        this.id=id;
        comment=new AtomicReference<>(null);
    }

    // ============================ getters & setters ============================ //

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

    public String getComment() {return comment.get();}

    public AtomicReference<String> getCommentReference() {
        return comment;
    }

    public void setComment(AtomicReference<String> comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // ============================ getters & setters ============================ //

}
