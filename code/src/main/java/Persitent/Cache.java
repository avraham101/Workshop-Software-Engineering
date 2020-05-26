package Persitent;

import Domain.Subscribe;
import Domain.User;

import java.util.concurrent.ConcurrentHashMap;

public class Cache {

    private static ConcurrentHashMap<Integer,User> connectedUsers;
    private static ConcurrentHashMap<String, Subscribe> subscribes;
    private static SubscribeDao subscribeDao;

    public Cache(){
        if(connectedUsers==null) {
            connectedUsers = new ConcurrentHashMap<>();
            subscribeDao = new SubscribeDao();
            subscribes = new ConcurrentHashMap<>();
        }
    }

    public void addConnectedUser(int newId, User user) {
        if(connectedUsers!=null) {
            connectedUsers.put(newId, user);
        }
    }

    /**
     * assumption this funcition called only if user is logeed
     * @param newId the id of the user
     */
    public synchronized boolean logedCounnectedUser(int newId) {
        if(connectedUsers!=null) {
            User user =  connectedUsers.get(newId);
            subscribes.put(user.getUserName() ,(Subscribe)user.getState());
            return true;
        }
        return false;
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
