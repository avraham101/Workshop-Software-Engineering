package DataAPI;

import Domain.Review;

public class ReviewData {
    private String store;
    private String productName;
    private String content;
    private String writer;

    public ReviewData(String storeName, String productName, String content) {
        this.store = storeName;
        this.productName = productName;
        this.content = content;
        this.writer=null;
    }

    public ReviewData(Review review) {
        this.writer=review.getWriter();
        this.content=review.getContent();
        this.productName=review.getProductName();
        this.store=review.getStore();

    }


    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
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

    public String getWriter() {
        return writer;
    }
}
