package Service;

import DataAPI.*;
import Domain.*;
import Systems.HashSystem;
import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;

import java.util.List;

public class ServiceAPI {

    private LogicManager logicManager;

    /**
     * use case 1.1 - Init Trading System
     * @param userName - the user name
     * @param password - the user password
     * @throws Exception - system crashed exception
     */
    public ServiceAPI(String userName, String password) throws Exception{
        logicManager = new LogicManager(userName, password);
    }

    public ServiceAPI(String userName, String password, PaymentSystem paymentSystem, SupplySystem supplySystem) throws Exception{
        this.logicManager = new LogicManager(userName, password,paymentSystem,supplySystem);
    }


    /**
     * hand shake to connect to the system
     * @return
     */
    public int connectToSystem(){
        return logicManager.connectToSystem();
    }
    /**
     * use case 2.2 - Register
     * @param userName - the user Name
     * @param password - the user password
     * @return true if the register complete, otherwise false
     */
    public boolean register(String userName, String password) {
        return logicManager.register(userName,password);
    }

    /***
     * use case 2.3 - Login
     * @param userName - the user Name
     * @param password - the user password
     * @return true if the user is logged to the system, otherwise false
     */
    public boolean login(int id,String userName, String password) {
        return logicManager.login(userName, password);
    }

    /**
     * use 2.4.1 - show the details about every store
     * @return - details of all the stores data
     */
    public List<StoreData> viewStores() {
        return logicManager.viewStores();
    }

    /**
     * use case 2.4.2 - show the products of a given store
     * @param storeName - the store that owns the products
     * @return - list of ProductData of the products in the store
     */
    public List<ProductData> viewProductsInStore(String storeName) {
        return logicManager.viewProductsInStore(storeName);
    }

    /**
     * use case 2.5 - Search product in store
     * @param filter - the filter chosen
     * @return - list of products after filer and sorter.
     */
    public List<ProductData> viewSpasificProducts(Filter filter) {
        //filters -> price range, category ,*product rating, *store rating,
        return logicManager.viewSpecificProducts(filter);
    }

    /**
     * use case 2.7.1 - watch cart details
     * return the details about a cart
     * @return - the cart details
     */
    public CartData watchCartDetatils(int id){
        return logicManager.watchCartDetatils();
    }

    /**asassas
     * use case 2.7.2 - delete producr from cart
     * delete product from the cart
     * @param productName - the product to remove
     * @param storeName - the store that sale this product
     * @return - true if the delete work, false if not
     */
    public boolean deleteFromCart(int id,String productName,String storeName){
        return logicManager.deleteFromCart(productName, storeName);
    }

    /**
     * use case 2.7.3 edit amount of product
     * @param productName - the product to edit it's amount
     * @param storeName - the store of the product
     * @param newAmount - the new amount
     * @return - true if succeeded, false if not
     */
    public boolean editProductInCart(int id,String productName,String storeName,int newAmount){
        return logicManager.editProductInCart(productName, storeName, newAmount);
    }

    /**
     * use case 2.7.4 - add product to the cart
     * @param productName - the product to add
     * @param storeName - the store of the product
     * @param amount - the amount of the product that need to add to the cart
     * @return - true if added, false if not
     */
    public boolean addProductToCart(int id,String productName,String storeName,int amount){
        return logicManager.addProductToCart(productName, storeName, amount);
    }

    /**
     * use case 2.8 - purchase cart
     * @param paymentData - the payment data of this purchase
     * @param addressToDeliver - the addressToDiliver
     * @return true is the purchase succeeded, otherwise false
     */
    public boolean purchaseCart(int id,PaymentData paymentData, String addressToDeliver){
        return logicManager.purchaseCart(paymentData,addressToDeliver);
    }

    /**
     * use case 3.1 - Logout
     * @return true if the user logout
     */
    public boolean logout(int id){
        return logicManager.logout();
    }

    /***
     * use case 3.2 - Open Store
     * @param storeDetails - the details of the store
     * @return true if store is created
     */
    public boolean openStore(int id,StoreData storeDetails){
        return logicManager.openStore(storeDetails);
    }

    /***
     * use case 3.3 - write review
     * @param productName - the name of the product name
     * @param storeName - the store name
     * @param content - the content name
     * @return true if the review added, otherwise false
     */
    public boolean writeReview(int id,String storeName,String productName, String content){
        return logicManager.addReview(storeName,productName, content);
    }

    //use case 3.5
    public boolean writeRequestToStore(int id,String storeName,String content){
        return logicManager.addRequest(storeName,content);
    }

    /**
     * use case 3.7 - watch purchase history
     * the function return the purchase list
     * @return the purchase list
     */
    public List<Purchase> watchMyPurchaseHistory(int id){
        return logicManager.watchMyPurchaseHistory();
    }

    /**
     * use case 4.1.1
     * @param id - user id
     * @param productData - data of product
     * @return if product was added
     */
    public boolean addProductToStore(int id,ProductData productData){
        return logicManager.addProductToStore(productData);
    }

    /**
     * use case 4.1.2
     * @param id- user id
     * @param productName - the name of the product name
     * @param storeName - the store name
     * @return if product was removed
     */
    public boolean removeProductFromStore(int id,String storeName,String productName){
        return logicManager.removeProductFromStore(storeName,productName);
    }

    /**
     * use case 4.1.3
     * @param id - user id
     * @param productData - data of product
     * @return if product was edited
     */
    public boolean editProductFromStore(int id,ProductData productData){
        return logicManager.editProductFromStore(productData);
    }

    /**
     * use case 4.3
     * @param id - user id
     * @param storeName - name of store
     * @param userName - name of user
     * @return if new owner was managed
     */
    public boolean manageOwner(int id,String storeName,String userName){
        return logicManager.manageOwner(storeName,userName);
    }

    /**
     * use case 4.5- add manager to store
     * @param id - of user
     * @param storeName - name of store
     * @param userName - name of user
     * @return if the manger was added
     */
    public boolean addManagerToStore(int id,String storeName,String userName){
        return logicManager.addManager(userName,storeName);
    }

    /**
     * use case 4.6.1 - add permissions
     * @param id of current user
     * @param permissions to add
     * @param storeName of store
     * @param userName of user
     * @return
     */
    public boolean addPermissions(int id,List<PermissionType> permissions, String storeName, String userName){
        return logicManager.addPermissions(permissions,storeName,userName);
    }

    /**
     * use case 4.6.2 - remove permissions
     * @param id
     * @param permissions
     * @param storeName
     * @param userName
     * @return if permissions were removed
     */
    public boolean removePermissions(int id,List<PermissionType> permissions, String storeName, String userName){
        return logicManager.removePermissions(permissions,storeName,userName);
    }

    /**
     * use case 4.7 - remove manger
     * @param id
     * @param userName
     * @param storeName
     * @return if the manager was removed and also the managers he managed
     */
    public boolean removeManager(int id,String userName,String storeName){
        return logicManager.removeManager(userName,storeName);
    }

    /**
     * use case 4.9.1 - watch request
     * @param id
     * @param storeName
     * @return the request of the store to watch
     */
    public List<Request> watchRequestsOfStore(int id,String storeName){
        return logicManager.viewStoreRequest(storeName);
    }

    /**
     * use case 4.9.2 -reply request
     * @param id of user
     * @param requestId
     * @param content
     * @param storeName
     * @return the request with the apply
     */
    public Request answerRequest(int id,int requestId,String content, String storeName){
        return logicManager.replayRequest(storeName,requestId,content);
    }

    /**
     * use case 4.10 - watch Store History by store owner
     * @param storeName - the store name to watch history
     * @return the purchase list
     */
    public List<Purchase> watchStoreHistory(int id,String storeName){
        return logicManager.watchStorePurchasesHistory(storeName);
    }

    /**
     * use case 6.4.1 - admin watch history purchases of some user
     * @param userName - the user that own the purchases
     * @return - list of purchases that of the user
     */
    public List<Purchase> AdminWatchUserPurchasesHistory(int id,String userName){
        return logicManager.watchUserPurchasesHistory(userName);
    }

    /**
     * use case 6.4.2 - admin watch history purchases of some user
     * @param storeName - the name of the store that own the purchases
     * @return - list of purchases that of the store
     */
    public List<Purchase> AdminWatchStoreHistory(int id,String storeName){
        return logicManager.watchStorePurchasesHistory(storeName);
    }


}
