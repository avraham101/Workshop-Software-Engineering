package Publisher;

import Domain.Notification.Notification;

import java.util.ArrayList;

public interface Sender {
    void send(String userId, ArrayList<Notification> notification);
}
