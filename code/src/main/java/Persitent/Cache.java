package Persitent;

import Domain.Subscribe;
import Domain.User;

import java.util.concurrent.ConcurrentHashMap;

public class Cache {

    protected static ConcurrentHashMap<Integer,User> connectedUsers;
    private static SubscribeDao subscribeDao;

    public Cache(){
        if(connectedUsers==null) {
            connectedUsers = new ConcurrentHashMap<>();
            subscribeDao = new SubscribeDao();
        }
    }

    public void addConnectedUser(int newId, User user) {
        if(connectedUsers!=null) {
            connectedUsers.put(newId, user);
        }
    }

    public User findUser(int id) {
        if(connectedUsers!=null) {
            return connectedUsers.get(id);
        }
        return null;
    }

    public Subscribe findSubscribe(String userName) {
        Subscribe sub = findSubscribeInCache(userName);
        if(sub!=null)
            return sub;
        return subscribeDao.find(userName) ;
    }

    public Subscribe findSubscribeInCache(String userName){
        for(User user : connectedUsers.values()) {
            if (user!=null && user.getUserName()!=null && user.getUserName().equals(userName))
                return (Subscribe) user.getState();
        }
            return null;
    }

}
