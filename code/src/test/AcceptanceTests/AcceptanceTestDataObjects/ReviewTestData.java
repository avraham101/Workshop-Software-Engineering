package AcceptanceTests.AcceptanceTestDataObjects;

public class ReviewTestData {
    private UserTestData writer;
    private String content;

    public ReviewTestData(UserTestData writer, String content) {
        this.writer = writer;
        this.content = content;
    }

    public UserTestData getWriter() {
        return writer;
    }

    public String getContent() {
        return content;
    }
}
