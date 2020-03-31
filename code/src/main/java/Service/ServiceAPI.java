package Service;

import DataAPI.*;
import Domain.*;
import jdk.nashorn.internal.ir.RuntimeNode;

import java.util.List;
import java.util.logging.Filter;

public class ServiceAPI {
    private LogicManager logicManager;

    public ServiceAPI() {
        logicManager=new LogicManager();
    }

    /**
     * use case 2.2
     * @param userName - the user Name
     * @param password - the user password
     * @return true if the register complete, otherwise false
     */
    public boolean register(String userName, String password) {
        return logicManager.register(userName,password);
    }

    /***
     * use case 2.3
     * @param userName - the user Name
     * @param password - the user password
     * @return true if the user is logged to the system, otherwise false
     */
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

    //TODO use case 2.7.1
    public CartData watchCartDetatils(){
        return null;
    }

    //TODO use case 2.7.2
    public boolean deleteFromCart(String productName,String StoreName){
        return false;
    }

    //TODO use case 2.7.3
    public boolean editProductInCart(String productName,String StoreName,int newAmount){
        return false;
    }

    //TODO use case 2.7.4
    public boolean addProductTOCart(String productName,String StoreName,int amount){
        return false;
    }

    //TODO use case 2.8
    public boolean purchaseCart(PaymentData paymentData, DeliveryData deliveryData){
        return false;
    }

    //TODO use case 3.1
    public boolean logout(){
        return logicManager.logout();
    }

    /***
     * use case 3.2
     * @param storeDetails - the details of the store
     * @return true if store is created
     */
    //store data same data as use case 2.4.1
    public boolean openStore(StoreData storeDetails){
        return logicManager.openStore(storeDetails);
    }

    //TODO use case 3.3
    public boolean writeReview(String productName,String StoreName,String content){
        return false;
    }

    //TODO use case 3.5
    public boolean writeRequestToStore(String storeName,String content){
        return false;
    }

    //TODO use case 3.7
    public List<Purchase> watchMyPurchaseHistory(){
        return null;
    }

    //TODO use case 4.1.1
    public boolean addProductToStore(String storeName,ProductData productData){
        return false;
    }

    //TODO use case 4.1.2
    public boolean removeProductFromStore(String storeName,String ProductName){
        return false;
    }

    //TODO use case 4.1.3
    public boolean EditProductFromoStore(String storeName,ProductData productData){
        return false;
    }

    //TODO use case 4.5
    public boolean addManagerToStore(String storeName,String userName){
        return false;
    }

    //TODO use case 4.6.1 //talk to yuval before implement
    public boolean addPerMissions(List<PermissionType> permissions, String storeName, String userName){
        return false;
    }

    //TODO use case 4.6.2 //talk to yuval before implement
    public boolean removePermissions(List<PermissionType> permissions, String storeName, String userName){
        return false;
    }

    //TODO use case 4.7
    public boolean removeOwner(String userName,String storeName){
        return false;
    }

    //TODO use case 4.9.1
    public List<Request> watchRequestsOfStore(String storeName){
        return null;
    }

    //TODO use case 4.9.2
    public boolean answerRequest(Request request, String StoreName, String content){
        return false;
    }

    //TODO use case 4.10
    public List<Purchase> watchStoreHistory(String storeName){
        return null;
    }

    //TODO use case 6.4.1
    public List<Purchase> watchUserPurchasesHistory(String userName){
        return null;
    }

    //TODO use case 6.4.2
    public List<Purchase> watchUserhistory(String username){
        return null;
    }
}
