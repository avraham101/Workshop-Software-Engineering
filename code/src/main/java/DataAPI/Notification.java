package DataAPI;

public class Notification<T> {
    OpCode reason;
    T value;

    public Notification(T value, OpCode reason) {
        this.value = value;
        this.reason = reason;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public OpCode getReason() {
        return reason;
    }

    public void setReason(OpCode reason) {
        this.reason = reason;
    }

}
