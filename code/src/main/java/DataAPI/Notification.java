package DataAPI;

public class Notification<T> {
    OpCode reason;
    T value;
    Integer id;

    public Notification(T value, OpCode reason,Integer id) {
        this.value = value;
        this.reason = reason;
        this.id=id;
    }
    public Notification(T value, OpCode reason) {
        this.value = value;
        this.reason = reason;
        this.id=id;
    }


    public T getValue() {
        return value;
    }

    public OpCode getReason() {
        return reason;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if(this.id==null)
            this.id = id;
    }
}
