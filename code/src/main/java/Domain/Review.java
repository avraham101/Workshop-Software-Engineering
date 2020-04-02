package Domain;

import java.util.Objects;

public class Review {
    private Subscribe writer;
    private String content;

    public Review(Subscribe writer, String content) {
        this.writer = writer;
        this.content = content;
    }

    public Subscribe getWriter() {
        return writer;
    }

    public void setWriter(Subscribe writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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
