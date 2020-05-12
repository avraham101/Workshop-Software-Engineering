package Publisher;

import DataAPI.Notification;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.util.ArrayList;


public class Publisher {

    private Sender sender;

    public Publisher(Sender sender) {
        this.sender=sender;
    }

    public void update(String userId, ArrayList<Notification> notification){
       sender.send(userId,notification);
    }
}
