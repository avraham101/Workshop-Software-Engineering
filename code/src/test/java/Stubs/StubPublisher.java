package Stubs;

import Domain.Notification;
import Publisher.Publisher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StubPublisher extends Publisher {

    private HashMap<Integer,List<Notification>> notificationList;
    public StubPublisher() {
        super(null);
        notificationList=new HashMap<>();
    }

    @Override
    public void update(String userId, ArrayList<Notification> notification) {
        int id=Integer.parseInt(userId);
        if(!notificationList.containsKey(id))
            notificationList.put(id,new ArrayList<>());
        notificationList.get(id).addAll(notification);
    }

    public HashMap<Integer, List<Notification>> getNotificationList() {
        return notificationList;
    }
}
