package Domain;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="request")
public class Request implements Serializable {

    public Request() {
    }

    @Column(name="sender",nullable = false)
    private String senderName;

    @Column(name="store")
    private String storeName;

    @Column(name="content",nullable = false)
    private String content;

    @Column(name="comment")
    private String comment;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    public Request(String senderName, String storeName, String content,int id) {
        this.senderName = senderName;
        this.storeName=storeName;
        this.content = content;
        //this.id=id;
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

//    public void setId(int id) {
//        this.id = id;
//    }

    public synchronized boolean setComment(String comment) {
        if(this.comment==null) {
            this.comment = comment;
            return true;
        }
        return false;
    }

    // ============================ getters & setters ============================ //

}
