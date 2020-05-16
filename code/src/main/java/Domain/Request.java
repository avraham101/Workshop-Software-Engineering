package Domain;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;
import javax.persistence.*;

@Entity
@Table(name="request")
public class Request implements Serializable {

    public Request() {
    }

    @Column(name="sender",nullable = false)
    private String senderName;

    @Id
    @Column(name="store",nullable = false)
    private String storeName;

    @Column(name="content",nullable = false)
    private String content;

    @Column(name="comment")
    private String comment;

    @Id
    @Column(name="id")
    private int id;

    public Request(String senderName, String storeName, String content,int id) {
        this.senderName = senderName;
        this.storeName=storeName;
        this.content = content;
        this.id=id;
    }

    // ============================ getters & setters ============================ //

    public String getSenderName() {
        return senderName;
    }



    public String getStoreName() {
        return storeName;
    }

    public String getContent() {
        return content;
    }

    public String getComment() {
        return comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public synchronized boolean setComment(String comment) {
        if(this.comment==null) {
            this.comment = comment;
            return true;
        }
        return false;
    }

    // ============================ getters & setters ============================ //

}
