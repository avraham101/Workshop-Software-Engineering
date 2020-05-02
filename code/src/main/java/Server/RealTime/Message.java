package Server.RealTime;

public class Message {
    private String content;
    private MessageType type;

    public enum MessageType {
        CONNECT,LEAVE,DATA
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
