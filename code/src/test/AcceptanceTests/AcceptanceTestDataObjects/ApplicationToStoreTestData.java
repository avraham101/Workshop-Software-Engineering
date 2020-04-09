package AcceptanceTests.AcceptanceTestDataObjects;

public class ApplicationToStoreTestData {
    private String writer;
    private String content;
    private String storeName;

    public ApplicationToStoreTestData(String storeName ,String writer, String content) {
        this.writer = writer;
        this.content = content;
        this.storeName = storeName;
    }

    public String getWriter() {
        return writer;
    }

    public String getContent() {
        return content;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplicationToStoreTestData that = (ApplicationToStoreTestData) o;

        if (writer != null ? !writer.equals(that.writer) : that.writer != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        return storeName != null ? storeName.equals(that.storeName) : that.storeName == null;
    }
}
