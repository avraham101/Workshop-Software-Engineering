package Domain;

import DataAPI.StoreData;
import Systems.*;
import Systems.PaymentSystem.PaymentSystem;
import Systems.PaymentSystem.ProxyPayment;
import Systems.SupplySystem.ProxySupply;
import Systems.SupplySystem.SupplySystem;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class LogicManager {
    private HashMap<String, Subscribe> users;
    private HashMap<String, Store> stores;
    private HashSystem hashSystem;
    private PaymentSystem paymentSystem;
    private SupplySystem supplySystem;
    private LoggerSystem loggerSystem;
    private User current;

    public LogicManager(HashMap<String, Subscribe> users, HashMap<String, Store> stores, User current) {
        this.users = users;
        this.stores = stores;
        this.current = current;
        try {
            hashSystem = new HashSystem();
            loggerSystem = new LoggerSystem();
            paymentSystem = new ProxyPayment();
            supplySystem = new ProxySupply();
            /**
             * use case 1.1
             */
            if(!paymentSystem.connect()) {
                throw new Exception("Payment System Crashed");
            }
            if(!supplySystem.connect()) {
                throw new Exception("Supply System Crashed");
            }
        } catch (Exception e) {
            System.exit(1);
        }
    }

    public LogicManager() {
        users = new HashMap<>();
        stores = new HashMap<>();
        try {
            hashSystem = new HashSystem();
            loggerSystem = new LoggerSystem();
            /**
             * use case 1.1
             */
            if(!paymentSystem.connect()) {
                throw new Exception("Payment System Crashed");
            }
            if(!supplySystem.connect()) {
                throw new Exception("Supply System Crashed");
            }
        } catch (Exception e) {
            System.exit(1);
        }
        current = new User();
    }

    public boolean register(String userName, String password) {
        //TODO add to logger
        if(!users.containsKey(userName)){
            try {
                password = hashSystem.encrypt(password);
                Subscribe subscribe =null;
                if(users.isEmpty())
                    subscribe = new Admin(userName, password);
                else
                    subscribe = new Subscribe(userName, password);
                users.put(userName,subscribe);
                return true;
            } catch (NoSuchAlgorithmException e) {
                //TODO add to logger
            }
        }
        return false;
    }

    public boolean login(String userName, String password) {
        if (users.containsKey(userName)) {
            try {
                password = hashSystem.encrypt(password);
                Subscribe subscribe = users.get(userName);
                if (subscribe.getPassword().compareTo(password) == 0) {
                    return current.login(subscribe);
                }
            } catch (NoSuchAlgorithmException e) {
                //TODO add to logger
            }
        }
        return false;
    }

    public boolean logout() {
        //TODO logger
        return current.logout();
    }

    /**
     * use case 3.2
     * @param storeDetails - the details of the the store
     * @return true if store is open, otherwise false.
     */
    public boolean openStore(StoreData storeDetails) {
        if(stores.containsKey(storeDetails.getName()))
            return false;
        Store store = current.openStore(storeDetails,paymentSystem, supplySystem);
        if(store != null) {
            stores.put(store.getName(),store);
        }
        return false;
    }
}
