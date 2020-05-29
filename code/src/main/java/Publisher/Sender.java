package Publisher;

import Domain.Notification.Notification;

import java.util.ArrayList;

public interface Sender {
    public void send(String userId, ArrayList<Notification> notification);
}
