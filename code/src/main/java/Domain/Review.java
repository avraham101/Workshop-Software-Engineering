package Domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="review")
public class Review implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="writer")
    private String writer;

    @Column(name="store")
    private String store;

    @Column(name="productName")
    private String productName;

    @Column(name="content")
    private String content;

    public Review(String writer, String store, String productName, String content) {
        this.writer = writer;
        this.store = store;
        this.productName = productName;
        this.content = content;
    }

    public Review() {
    }


    // ============================ getters & setters ============================ //


    public Integer getId() {
        return id;
    }

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
