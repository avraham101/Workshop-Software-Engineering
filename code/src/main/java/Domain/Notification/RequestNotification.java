package Domain.Notification;

import DataAPI.OpCode;
import Domain.Request;

import javax.persistence.*;

@Entity
@Table(name="request_notification")
public class RequestNotification extends Notification<Request>{

    @ManyToOne(cascade =CascadeType.REFRESH,fetch =FetchType.EAGER)
    @JoinColumn(name="request_id",referencedColumnName = "id")
    private Request request;

    public RequestNotification(Request value, OpCode reason) {
        super(reason);
        this.request=value;
    }

    public RequestNotification() {
    }

    @Override
    public Request getValue() {
        return request;
    }
}
