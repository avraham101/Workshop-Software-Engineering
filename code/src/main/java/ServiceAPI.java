import DataAPI.ProductData;
import DataAPI.StoreData;
import java.util.List;

public class ServiceAPI {
    private LogicManager logicManager;

    public ServiceAPI() {
        logicManager=new LogicManager();
    }

    //TODO use case 2.2
    public boolean register(String userName, String password) {
        return logicManager.register(userName,password);
    }

    //TODO use case 2.3
    public boolean login(String userName, String password) {
        return logicManager.login(userName, password);
    }

    //TODO use case 2.4.1
    public List<StoreData> viewStores() {
        return null;
    }

    //TODO use case 2.4.2
    public List<ProductData> viewProducts(String storeName) {
        return null;
    }

    //TODO use case 2.5
    //filters -> price range, category ,*product rating, *store rating,
    public List<ProductData> viewSpasificProducts(Filter filter) {
        return null;
    }

}
