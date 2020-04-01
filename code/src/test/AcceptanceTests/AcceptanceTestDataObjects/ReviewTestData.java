package AcceptanceTests.AcceptanceTestDataObjects;

public class ReviewData {
    private UserData writer;
    private String content;

    public ReviewData(UserData writer, String content) {
        this.writer = writer;
        this.content = content;
    }

    public UserData getWriter() {
        return writer;
    }

    public String getContent() {
        return content;
    }
}
