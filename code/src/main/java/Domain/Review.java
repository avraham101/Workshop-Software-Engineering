package Domain;

public class Review {

    private String writer;
    private String store;
    private String productName;
    private String content;


    public Review(String writer, String store, String productName, String content) {
        this.writer = writer;
        this.store = store;
        this.productName = productName;
        this.content = content;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
