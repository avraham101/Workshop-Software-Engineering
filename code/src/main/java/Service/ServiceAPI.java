package Service;

import DataAPI.*;
import Domain.*;

import java.util.List;

public class ServiceAPI {
    private LogicManager logicManager;

    public ServiceAPI() {
        logicManager=new LogicManager();
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
    public boolean login(String userName, String password) {
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
    public CartData watchCartDetatils(){
        return logicManager.watchCartDetatils();
    }

    /**
     * use case 2.7.2 - delete producr from cart
     * delete product from the cart
     * @param productName - the product to remove
     * @param storeName - the store that sale this product
     * @return - true if the delete work, false if not
     */
    public boolean deleteFromCart(String productName,String storeName){
        return logicManager.deleteFromCart(productName, storeName);
    }

    /**
     * use case 2.7.3 edit amount of product
     * @param productName - the product to edit it's amount
     * @param storeName - the store of the product
     * @param newAmount - the new amount
     * @return - true if succeeded, false if not
     */
    public boolean editProductInCart(String productName,String storeName,int newAmount){
        return logicManager.editProductInCart(productName, storeName, newAmount);
    }

    /**
     * use case 2.7.4 - add product to the cart
     * @param productName - the product to add
     * @param storeName - the store of the product
     * @param amount - the amount of the product that need to add to the cart
     * @return - true if added, false if not
     */
    public boolean addProductToCart(String productName,String storeName,int amount){
        return logicManager.aadProductToCart(productName, storeName, amount);
    }

    /**
     * use case 2.8 - purchase cart
     * @param paymentData - the payment data of this purchase
     * @param addressToDeliver - the addressToDiliver
     * @return true is the purchase succeeded, otherwise false
     */
    public boolean purchaseCart(PaymentData paymentData, String addressToDeliver){
        return logicManager.purchaseCart(paymentData,addressToDeliver);
    }

    /**
     * use case 3.1 - Logout
     * @return true if the user logout
     */
    public boolean logout(){
        return logicManager.logout();
    }

    /***
     * use case 3.2 - Open Store
     * @param storeDetails - the details of the store
     * @return true if store is created
     */
    public boolean openStore(StoreData storeDetails){
        return logicManager.openStore(storeDetails);
    }

    /***
     * use case 3.3 - write review
     * @param productName - the name of the product name
     * @param storeName - the store name
     * @param content - the content name
     * @return true if the review added, otherwise false
     */
    public boolean writeReview(String storeName,String productName, String content){
        return logicManager.addReview(storeName,productName, content);
    }

    //TODO use case 3.5
    public boolean writeRequestToStore(String storeName,String content){
        return false;
    }

    /**
     * use case 3.7 - watch purchase history
     * the fucntion return the purchase list
     * @return the purchase list
     */
    public List<Purchase> watchMyPurchaseHistory(){
        return logicManager.watchMyPurchaseHistory();
    }

    //use case 4.1.1
    public boolean addProductToStore(ProductData productData){
        return logicManager.addProductToStore(productData);
    }

    //use case 4.1.2
    public boolean removeProductFromStore(String storeName,String productName){
        return logicManager.removeProductFromStore(storeName,productName);
    }

    // use case 4.1.3
    public boolean editProductFromStore(ProductData productData){
        return logicManager.editProductFromStore(productData);
    }

    //TODO use case 4.3
    public boolean manageOwner(String storeName,String userName){
        return false;
    }



    //TODO use case 4.5
    public boolean addManagerToStore(String storeName,String userName){
        return logicManager.addManager(storeName,userName);
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
    public boolean answerRequest(Request request, String StoreName){
        return false;
    }

    /**
     * use case 4.10 - watch Store History by store owner
     * @param storeName - the store name to watch history
     * @return the purchase list
     */
    public List<Purchase> watchStoreHistory(String storeName){
        return logicManager.watchStoreHistory(storeName);
    }

    /**
     * use case 6.4.1 - admin watch history purchases of some user
     * @param userName - the user that own the purchases
     * @return - list of purchases that of the user
     */
    public List<Purchase> AdminWatchUserPurchasesHistory(String userName){
        return logicManager.AdminWatchUserPurchasesHistory(userName);
    }

    /**
     * use case 6.4.2 - admin watch history purchases of some user
     * @param storeName - the name of the store that own the purchases
     * @return - list of purchases that of the store
     */
    public List<Purchase> AdminWatchStoreHistory(String storeName){
        return logicManager.AdminWatchStoreHistory(storeName);
    }
}
