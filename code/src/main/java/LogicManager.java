import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class LogicManager {
    private HashMap<String,Subscribe> users;
    private HashMap<String,Store> stores;
    private HashSystem hashSystem;
    private LoggerSystem loggerSystem;
    private User current;

    public LogicManager() {
        users = new HashMap<>();
        stores = new HashMap<>();
        try {
            //TODO add here all external systems
            hashSystem = new HashSystem();
            loggerSystem = new LoggerSystem();
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
}
