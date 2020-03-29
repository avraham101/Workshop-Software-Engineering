public class Request {

    private Subscribe senderName;
    private Store store;
    private String content;
    private String comment;

    public Request(Subscribe senderName, Store store, String content) {
        this.senderName = senderName;
        this.store = store;
        this.content = content;
        comment=null;
    }

    public Subscribe getSenderName() {
        return senderName;
    }

    public void setSenderName(Subscribe senderName) {
        this.senderName = senderName;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
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
