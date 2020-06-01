package Domain.Notification;

import DataAPI.OpCode;

import javax.persistence.*;

@Entity
@Table(name="owner_added_notification")
public class AddOwnerNotification extends Notification<String> {

    @Column(name="store")
    private String store;

    public AddOwnerNotification(String store) {
        super(OpCode.Add_Owner);
        this.store = store;
    }

    public AddOwnerNotification() {
    }

    @Override
    public String getValue() {
        return store;
    }
}
