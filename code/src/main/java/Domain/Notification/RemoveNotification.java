package Domain.Notification;

import DataAPI.OpCode;

import javax.persistence.*;

@Entity
@Table(name="remove_notification")
public class RemoveNotification extends Notification<String> {

    @Column(name="store")
    private String store;

    public RemoveNotification(String value, OpCode reason) {
        super(reason);
        this.store=value;
    }

    public RemoveNotification() {
    }

    @Override
    public String getValue() {
        return store;
    }
}
