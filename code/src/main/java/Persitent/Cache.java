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

    public User findUser(int id){
        return connectedUsers.get(id);
    }

    public Subscribe findSubscribe(String username){
        for(User user : this.connectedUsers.values())
            if(user.getUserName().equals(username))
                return (Subscribe) user.getState();
        return null;
    }

    public Store findStore(String storeName){
        Store store = stores.get(storeName);
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

    public static Cache getInstance(){
        if(instance==null){
            instance = new Cache();
        }
        return instance;
    }
}
