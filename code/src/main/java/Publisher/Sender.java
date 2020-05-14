package Publisher;

import DataAPI.Notification;

import java.util.ArrayList;

public interface Sender {
    public void send(String userId, ArrayList<Notification> notification);
}
