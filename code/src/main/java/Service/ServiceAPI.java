package Service;

import DataAPI.*;
import Domain.*;
import Publisher.Publisher;
import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
public class ServiceAPI {

    private LogicManager logicManager;

    /**
     * use case 1.1 - Init Trading System
     * acceptance test class : initialStartTest
     * @param userName - the user name
     * @param password - the user passwords
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
     * acceptance test class : RegisterTest
     * @param userName - the user Name
     * @param password - the user password
     * @return Response with true value if the register complete, otherwise false
     */
    public Response<Boolean> register(String userName, String password) {
        return logicManager.register(userName,password);
    }

    /***
     * use case 2.3 - Login
     * acceptance test class : LoginTest
     * @param userName - the user Name
     * @param password - the user password
     * @return Response with true value if the user is logged to the system, otherwise false
     */
    public Response<Boolean> login(int id,String userName, String password) {
        return logicManager.login(id,userName, password);
    }

    /**
     * use 2.4.1 - show the details about every store
     * acceptance test class : GetStoreInfo
     * @return - details of all the stores data
     */
    public Response<List<StoreData>> viewStores() {
        return logicManager.viewStores();
    }

    /**
     * use case 2.4.2 - show the products of a given store
     * @param storeName - the store that owns the products
     * @return - list of ProductData of the products in the store
     */
    public Response<List<ProductData>> viewProductsInStore(String storeName) {
        return logicManager.viewProductsInStore(storeName);
    }

    /**
     * use case 2.5 - Search product in store
     * acceptance test class : FilterProductsInStoresTest
     * @param filter - the filter chosen
     * @return - list of products after filer and sorter.
     */
    public Response<List<ProductData>> viewSpasificProducts(Filter filter) {
        //filters -> price range, category ,*product rating, *store rating,
        return logicManager.viewSpecificProducts(filter);
    }

    /**
     * use case 2.7.1 - watch cart details
     * acceptance test class : EditCartTest
     * return the details about a cart
     * @return - the cart details
     */
    public Response<CartData> watchCartDetatils(int id){
        return logicManager.watchCartDetails(id);
    }

    /**
     * use case 2.7.2 - delete product from cart
     * acceptance test class : EditCartTest
     * delete product from the cart
     * @param productName - the product to remove
     * @param storeName - the store that sale this product
     * @return - true if the delete work, false if not
     */
    public Response<Boolean> deleteFromCart(int id,String productName,String storeName){
        return logicManager.deleteFromCart(id,productName, storeName);
    }

    /**
     * use case 2.7.3 - edit amount of product
     * acceptance test class : EditCartTest
     * @param productName - the product to edit it's amount
     * @param storeName - the store of the product
     * @param newAmount - the new amount
     * @return - true if succeeded, false if not
     */
    public Response<Boolean> editProductInCart(int id,String productName,String storeName,int newAmount){
        return logicManager.editProductInCart(id,productName, storeName, newAmount);
    }

    /**
     * use case 2.7.4 - add product to the cart
     * acceptance test class : AddToCartTest
     * @param productName - the product to add
     * @param storeName - the store of the product
     * @param amount - the amount of the product that need to add to the cart
     * @return - true if added, false if not
     */
    public Response<Boolean> addProductToCart(int id,String productName,String storeName,int amount){
        return logicManager.addProductToCart(id,productName, storeName, amount);
    }

    /**
     * use case 2.8 - purchase cart
     * acceptance test class : BuyCartTest
     * @param paymentData - the payment data of this purchase
     * @param addressToDeliver - the addressToDiliver
     * @return true is the purchase succeeded, otherwise false
     */

    public Response<Boolean> purchaseCart(int id, String country, PaymentData paymentData, String addressToDeliver){
        return logicManager.purchaseCart(id, country, paymentData,addressToDeliver);
    }

    /**
     * use case 3.1 - Logout
     * acceptance test class : LogoutTest
     * @return true if the user logout
     */
    public Response<Boolean> logout(int id){
        return logicManager.logout(id);
    }

    /***
     * use case 3.2 - Open Store
     * acceptance test class : OpenStoreTest
     * @param storeDetails - the details of the store
     * @return true if store is created
     */
    public Response<Boolean> openStore(int id,StoreData storeDetails){
        return logicManager.openStore(id,storeDetails);
    }

    /***
     * use case 3.3 - write review
     * acceptance test class : WriteReviewOnProductTest
     * @param productName - the name of the product name
     * @param storeName - the store name
     * @param content - the content name
     * @return true if the review added, otherwise false
     */
    public Response<Boolean> writeReview(int id,String storeName,String productName, String content){
        return logicManager.addReview(id,storeName,productName, content);
    }

    /**
     * use case 3.5 - write a request to specific store
     * acceptance test class : ApplicationToStoreTest
     * @param id - user id
     * @param storeName - the store name
     * @param content - the content of the request
     * @return true if the review added, otherwise false
     */

    public Response<Boolean> writeRequestToStore(int id,String storeName,String content){
        return logicManager.addRequest(id,storeName,content);
    }

    /**
     * use case 3.7 - watch purchase history
     * acceptance test class : WatchUserPurchaseHistoryTest
     * the function return the purchase list
     * @return the purchase list
     */
    public Response<List<Purchase>> watchMyPurchaseHistory(int id){
        return logicManager.watchMyPurchaseHistory(id);
    }

    /**
     * use case 4.1.1
     * acceptance test class : EditStore
     * @param id - user id
     * @param productData - data of product
     * @return if product was added
     */
    public Response<Boolean> addProductToStore(int id,ProductData productData){
        return logicManager.addProductToStore(id,productData);
    }

    /**
     * use case 4.1.2
     * acceptance test class : EditStore
     * @param id- user id
     * @param productName - the name of the product name
     * @param storeName - the store name
     * @return if product was removed
     */
    public Response<Boolean> removeProductFromStore(int id,String storeName,String productName){
        return logicManager.removeProductFromStore(id,storeName,productName);
    }

    /**
     * use case 4.1.3
     * acceptance test class : EditStore
     * @param id - user id
     * @param productData - data of product
     * @return if product was edited
     */
    public Response<Boolean> editProductFromStore(int id,ProductData productData){
        return logicManager.editProductFromStore(id,productData);
    }

    /**
     * acceptance test class : AddDiscountTest
     * use case 4.2.1.1 - add discount to store
     * @param id - user id
     * @param discountData - data of the new discount to add
     * @param storeName - name of the store to add the discount to
     * @return
     */
    public Response<Boolean> addDiscount(int id,String discountData,String storeName){
        return logicManager.addDiscount(id,discountData,storeName);
    }

    /**
     * acceptance test class : ViewDeleteDiscountTest
     * 4.2.1.2 - remove discount
     * @param id - user id
     * @param discountId - id of the discount ro delete
     * @param storeName - name of the store to remove the discount from
     */
    public Response<Boolean> deleteDiscountFromStore(int id,int discountId,String storeName){
        return logicManager.deleteDiscountFromStore(id,discountId,storeName);
    }

    /**
     * acceptance test class : ViewDeleteDiscountTest
     * 4.2.1.3 - view discounts
     * @param storeName - name of the store to get the discounts from
     */
    public Response<HashMap<Integer,String>> viewDiscounts(String storeName){
        return logicManager.viewDiscounts(storeName);
    }

    /**
     * acceptance test class : UpdatePurchasePolicyTest‏
     * use case 4.2.2.1 - update policy of a store
     * @param id - the id of the user
     * @param policyData - the data about the policy
     * @param storeName - the name of the store
     * @return - true if added. false is not.
     */
    public Response<Boolean> upadtePolicy(int id, String policyData, String storeName) {
        return logicManager.updatePolicy(id,policyData,storeName);
    }

    /**
     * acceptance test class : UpdatePurchasePolicyTest‏
     * use case 4.2.2.2 - view the policy of a store
     * @param storeName - the name of the store
     * @return - string of the policy
     */
    public Response<String> viewPolicy(String storeName) {
        return logicManager.viewPolicy(storeName);
    }

    /**
     * use case 4.3
     * acceptance test class : AppointAnotherOwnerToStoreTest
     * @param id - user id
     * @param storeName - name of store
     * @param userName - name of user
     * @return if new owner was managed
     */
    public Response<Boolean> manageOwner(int id,String storeName,String userName){
        return logicManager.manageOwner(id,storeName,userName);
    }

    /**
     * use case 4.5- add manager to store
     * acceptance test class : AppointManagerTest
     * @param id - of user
     * @param storeName - name of store
     * @param userName - name of user
     * @return if the manger was added
     */
    public Response<Boolean> addManagerToStore(int id,String storeName,String userName){
        return logicManager.addManager(id,userName,storeName);
    }

    /**
     * use case 4.6.1 - add permissions
     * acceptance test class : EditPermissionTest
     * @param id of current user
     * @param permissions to add
     * @param storeName of store
     * @param userName of user
     * @return
     */
    public Response<Boolean> addPermissions(int id,List<PermissionType> permissions, String storeName, String userName){
        return logicManager.addPermissions(id,permissions,storeName,userName);
    }

    /**
     * use case 4.6.2 - remove permissions
     * acceptance test class : EditPermissionTest
     * @param id
     * @param permissions
     * @param storeName
     * @param userName
     * @return if permissions were removed
     */
    public Response<Boolean> removePermissions(int id,List<PermissionType> permissions, String storeName, String userName){
        return logicManager.removePermissions(id,permissions,storeName,userName);
    }

    /**
     * use case 4.7 - remove manger
     * acceptance test class : DeleteManagerTest
     * @param id
     * @param userName
     * @param storeName
     * @return if the manager was removed and also the managers he managed
     */
    public Response<Boolean> removeManager(int id,String userName,String storeName){
        return logicManager.removeManager(id,userName,storeName);
    }

    /**
     * use case 4.9.1 - watch request
     * acceptance test class : ViewAndReplyApplicationToStore
     * @param id
     * @param storeName
     * @return the request of the store to watch
     */
    public Response<List<RequestData>> watchRequestsOfStore(int id,String storeName){
        return logicManager.viewStoreRequest(id,storeName);
    }

    /**
     * use case 4.9.2 - reply request
     * acceptance test class : ViewAndReplyApplicationToStore
     * @param id of user
     * @param requestId
     * @param content
     * @param storeName
     * @return the request with the apply
     */
    public Response<RequestData> answerRequest(int id,int requestId,String content, String storeName){
        return logicManager.replayRequest(id,storeName,requestId,content);
    }

    /**
     * use case 4.10 - watch Store History by store owner
     * acceptance test class : GetStorePurchaseHistoryTest
     * @param storeName - the store name to watch history
     * @return the purchase list
     */
    public Response<List<Purchase>> watchStoreHistory(int id,String storeName){
        return logicManager.watchStorePurchasesHistory(id,storeName);
    }

    /**
     * use case 6.4.1 - admin watch history purchases of some user
     * acceptance test class : AdminPurchaseHistory
     * @param userName - the user that own the purchases
     * @return - list of purchases that of the user
     */
    public Response<List<Purchase>> AdminWatchUserPurchasesHistory(int id,String userName){
        return logicManager.watchUserPurchasesHistory(id,userName);
    }

    /**
     * use case 6.4.2 - admin watch history purchases of some user
     * acceptance test class : AdminPurchaseHistory
     * @param storeName - the name of the store that own the purchases
     * @return - list of purchases that of the store
     */
    public Response<List<Purchase>> AdminWatchStoreHistory(int id,String storeName){
        return logicManager.watchStorePurchasesHistory(id, storeName);
    }

    /**
     * acceptance test class : UserAdministrationInfo
     * returns all the stores managed by user
     * @param id user id
     * @return the stores managed by user
     */
    public Response<List<StoreData>> getStoresManagedByUser(int id){
        return logicManager.getStoresManagedByUser(id);
    }


    /**
     * acceptance test class : UserAdministrationInfo
     * @param id user's id
     * @param storeName store name
     * @return list of user's permissions for given store
     * if regular manager -> empty list
     * not a manager -> null
     */
    public Response<Set<StorePermissionType>> getPermissionsForStore(int id, String storeName){
        return logicManager.getPermissionsForStore(id,storeName);

    }

    /**
     * Acceptance test in ManagerPermissionsTest
     * return all the managers of a specific store
     * @return managers of specific store
     */
    public Response<List<String>> getManagersOfStore(String storeName){
        return logicManager.getManagersOfStore(storeName);
    }

    /**
     * Acceptance test in ManagerPermissionsTest
     * return all the managers of a specific store managed by user with id, id
     * @return managers of specific store
     */
    public Response<List<String>> getManagersOfStoreUserManaged(int id,String storeName){
        return logicManager.getManagersOfStoreUserManaged(id,storeName);
    }

    public void setPublisher(Publisher pub) {
        logicManager.setPublisher(pub);
    }

    public void deleteRecivedNotifications(int id,List<Integer> notificationsId){
        logicManager.deleteReceivedNotifications(id,notificationsId);
    }

    /**
     * Acceptance test in UserAdministrationInfo
     * get all the users for the admin
     * @param id - the id of the admin
     * @return - allt he users
     */
    public Response<List<String>> getAllUsers(int id) {
        return logicManager.getAllUsers(id);
    }

    public Response<Boolean> getMyNotification(int id) {
       return logicManager.getMyNotification(id);
    }

    /**
     * get the revenue of the trading system today
     * @param id - the id of the user
     * @return - the revenue today
     */
    public Response<Double> getRevenueToday(int id) {
        return logicManager.getRevenueToday(id);
    }

    /**
     * return the revenue of the trading system bu given day
     * @param id - the id of the user
     * @param day - the day we want
     * @return - the total revenue
     */
    public Response<Double> getRevenueByDay(int id, DateData day) {
        return logicManager.getRevenueByDate(id, day);
    }
}
