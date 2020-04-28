package AcceptanceTests.AcceptanceTestDataObjects;

public class ReviewTestData {
    private String writer;
    private String content;

    public ReviewTestData(String writer, String content) {
        this.writer = writer;
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReviewTestData that = (ReviewTestData) o;

        if (writer != null ? !writer.equals(that.writer) : that.writer != null) return false;
        return content != null ? content.equals(that.content) : that.content == null;
    }
}
