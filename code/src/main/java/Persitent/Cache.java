package Persitent;

import Domain.Store;
import Domain.Subscribe;
import Domain.User;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Cache {

    private static Cache instance;
    private ConcurrentLinkedDeque<Store> storesQueue;
    private ConcurrentHashMap<String, Store> stores;
    private ConcurrentHashMap<Integer,User> connectedUsers;
    private ConcurrentHashMap<String, Subscribe> subscribes;
    private SubscribeDao subscribeDao;
    private StoreDao storeDao;

    private int DEQUEUE_LIMIT = 8;

    private Cache(){
        this.storesQueue = new ConcurrentLinkedDeque<>();
        this.stores = new ConcurrentHashMap<>();
        this.connectedUsers = new ConcurrentHashMap<>();
        this.subscribeDao = new SubscribeDao();
        this.storeDao = new StoreDao();
        this.subscribes = new ConcurrentHashMap<>();
    }

    public static Cache getInstance(){
        if(instance==null){
            instance = new Cache();
        }
        return instance;
    }



    public User findUser(int id){
        return connectedUsers.get(id);
    }


    public Subscribe findSubscribe(String userName){

        Subscribe sub = findSubscribeInCache(userName);
        if(sub!=null)
            return sub;
        return subscribeDao.find(userName) ;
    }

    public Subscribe findSubscribeInCache(String userName){
        for(User user : this.connectedUsers.values())
            if(user.getUserName().equals(userName))
                return (Subscribe) user.getState();
            return null;
    }

    public Store findStoreInCache(String storeName){
        return stores.get(storeName);
    }

    public Store findStore(String storeName){
        Store store = findStoreInCache(storeName);
        if(store==null){
            store = storeDao.find(storeName);
            addToCache(store, storesQueue);
        }
        return store;
    }

    /**
     * add an item to the cache in the relevant queue
     * @param value the item to add to cache
     * @param queue the queue in the cache to add to
     * @param <T> item's class
     */
    public <T> void addToCache(T value, ConcurrentLinkedDeque<T> queue){
        if(value != null) {
            if (queue.size() == DEQUEUE_LIMIT)
                queue.removeFirst();
            queue.addLast(value);
        }
    }


    public void addConnectedUser(int newId, User user) {
        this.connectedUsers.put(newId,user);
    }
}
