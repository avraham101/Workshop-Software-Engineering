package Domain.Notification;
import DataAPI.OpCode;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="approve_notification")
public class approve_notification extends Notification<List<String>> {

    @Column(name="store")
    private String store;

    @Column(name="owner")
    private String owner;

    public approve_notification(String store, String owner) {
        super(OpCode.Approve_Owner);
        this.store = store;
        this.owner = owner;
    }

    public approve_notification() {
    }

    @Override
    public List<String> getValue() {
        List<String> res=new ArrayList<>();
        res.add(store);
        res.add(owner);
        return res;
    }

    public String getStore() {
        return store;
    }

    public String getOwner() {
        return owner;
    }
}
