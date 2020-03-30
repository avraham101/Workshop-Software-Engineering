public class ServiceAPI {
    private LogicManager logicManager;

    public ServiceAPI() {
        logicManager=new LogicManager();
    }

    public boolean register(String userName, String password) {
        return logicManager.register(userName,password);
    }

    public boolean login(String userName, String password) {
        return logicManager.login(userName, password);
    }
}
