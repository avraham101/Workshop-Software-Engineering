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
}
