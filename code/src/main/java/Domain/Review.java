package Domain;

import java.util.Objects;

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

    // ============================ getters & setters ============================ //

    public String getStore() {
        return store;
    }

    public String getWriter() {
        return writer;
    }

    public String getProductName() {
        return productName;
    }

    public String getContent() {
        return content;
    }

    // ============================ getters & setters ============================ //

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(writer, review.writer) &&
                Objects.equals(content, review.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(writer, content);
    }
}
