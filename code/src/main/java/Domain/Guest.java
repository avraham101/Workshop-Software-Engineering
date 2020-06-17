package Domain;

import DataAPI.*;
import Domain.Discount.Discount;
import Domain.PurchasePolicy.PurchasePolicy;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Guest extends UserState {

    private Cart cart;

    public Guest() {
        this.cart=new Cart("Guest");
    }

    @Override
    public Cart getCart() {
        return this.cart;
    }

    public Guest(Cart cart) {
        this.cart = cart;
    }

    // ============================ getters & setters ============================ //

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    // ============================ getters & setters ============================ //

    /**
     * use case 2.3 - Login
     * @param user - The user who using the system
     * @param subscribe - The user state who need to be set
     * @return always true. The user state changed from Guest to Subscribe
     */
    @Override
    public boolean login(User user, Subscribe subscribe) {
        user.setState(subscribe);
        return true;
    }

    /**
     * use case 2.8 - purchase cart and savePurchases
     * @param buyer - the name of the user
     */
    @Override
    public void savePurchase(String buyer) {
        this.cart.savePurchases(buyer);
        this.cart = new Cart("Guest");
    }

    /**
     * use case 3.1 - Logout
     * @param user - the user who using the system
     * @return return always false, guest cant be logged from the system, he already offline
     */
    @Override
    public boolean logout(User user) {
        return false;
    }

    /**
     * use case 3.2 - open store
     * @param storeDetails - the details of the store
     * @return always null. guest cant open store.
     */
    @Override
    public Store openStore(StoreData storeDetails) {
        return null;
    }

    /**
     * use case 3.3 - write review
     * the function check if the product is purchased
     * @param storeName - the store name
     * @param productName - the product name
     * @return always false, dosent have purchase history
     */
    @Override
    public boolean isItPurchased(String storeName, String productName) {
        return false;
    }

    /**
     * use case 3.3 - add review
     * @param review - the review to add
     */
    @Override
    public boolean addReview(Review review) {
        return false;
    }



    /**
     * use case 3.5 - add request
     * @param storeName - The id of the store
     * @param content - The content of the request
     * @return true if success, false else
     */
    @Override
    public Request addRequest(String storeName, String content){
        return null;
    }

    /**
     * use case 3.7 - watch purchase history
     * the function return the purchase list
     * @return the purchase list
     */
    @Override
    public Response<List<Purchase>> watchMyPurchaseHistory() {
        return new Response<>(null,OpCode.Not_Login);
    }


    /**
     * use case 4.1.1 -add product
     * @param productData
     * @return false for guest
     */
    @Override
    public Response<Boolean> addProductToStore(ProductData productData) {
        return new Response<>(false, OpCode.Not_Login);
    }


    /**
     * use case 4.1.2  - remove product
     * @param storeName
     * @param productName
     * @return false always
     */
    @Override
    public Response<Boolean> removeProductFromStore(String storeName, String productName) {
        return new Response<>(false,OpCode.Not_Login);
    }

    /**
     * use case 4.1.3 - edit product details
     * @param productData
     * @return
     */
    @Override
    public Response<Boolean> editProductFromStore(ProductData productData) {
        return new Response<>(false,OpCode.Not_Login);
    }

    /**
     * use case 4.2.1.1 - add discount to store
     * @param storeName
     * @param discount
     * @return
     */
    @Override
    public Response<Boolean> addDiscountToStore(String storeName, Discount discount) {
        return new Response<>(false,OpCode.Not_Login);
    }

    /**
     * 4.2.1.2 - remove discount
     * @param discountId - id of the discount ro delete
     * @param storeName - name of the store to remove the discount from
     */
    @Override
    public Response<Boolean> deleteDiscountFromStore(int discountId, String storeName) {
        return new Response<>(false,OpCode.Not_Login);
    }

    /**
     * use case 4.2. - update policy
     * @param storeName - the name of the store
     * @param policy - the policy
     */
    @Override
    public Response<Boolean> updateStorePolicy(String storeName, PurchasePolicy policy) {
        return new Response<>(false,OpCode.Not_Login);
    }

    /**
     * use case 4.3.1 - manage owner
     * @param storeName the name of the store to be manager of
     * @param userName the user to be manager of the store
     * @return
     */
    @Override
    public Response<Boolean> addOwner(String storeName, String userName) {
        return new Response<>(false,OpCode.Not_Login);
    }

    @Override
    public Response<Boolean> approveManageOwner(String storeName, String userName) {
        return new Response<>(false,OpCode.Not_Login);
    }

    /**
     * use case 4.5 - add manager to store
     * @param youngOwner user to be owner
     * @param storeName
     * @return
     */
    @Override
    public Response<Boolean> addManager(Subscribe youngOwner, String storeName) {
        return new Response<>(false,OpCode.Not_Login);
    }

    /**
     * use case 4.6.1 - add permissions
     * @param permissions
     * @param storeName
     * @param userName
     * @return
     */
    @Override
    public Response<Boolean> addPermissions(List<PermissionType> permissions, String storeName, String userName) {
        return new Response<>(false,OpCode.Not_Login);
    }

    /**
     * use case 4.6.2 - remove permissions
     * @param permissions
     * @param storeName
     * @param userName
     * @return
     */
    @Override
    public Response<Boolean> removePermissions(List<PermissionType> permissions, String storeName, String userName) {
        return new Response<>(false,OpCode.Not_Login);
    }

    @Override
    public Response<Boolean> removeManager(Subscribe userName, String storeName) {
        return new Response<>(false,OpCode.Not_Login);
    }

    @Override
    public List<Request> viewRequest(Store store) {
        return null;
    }


    /**
     * use case 4.9.2 - reply request
     * @param storeName
     * @param requestID
     * @param content
     * @return
     */
    @Override
    public Response<Request> replayToRequest(String storeName, Integer requestID, String content) {
        return new Response<>(null,OpCode.Not_Login);
    }

    /**
     * use case 4.10 - watch Store History by store owner
     * @param storeName - the store name to watch history
     * @return the purchase list
     */
    public boolean canWatchStoreHistory(String storeName) {
        return false;
    }


    /**
     * use case 6.4.1 - watch user history
     * @return
     */
    @Override
    public boolean canWatchUserHistory() {
        return false;
    }

    @Override
    public List<Store> getMyManagedStores() {
        return null;
    }

    @Override
    public Set<StorePermissionType> getPermissionsForStore(String storeName) {
        return null;
    }

    /**
     * return all the managers of a specific store that user with id managed
     * @return managers of specific store
     */
    @Override
    public Response<List<String>> getManagersOfStoreUserManaged(String storeName) {
        return new Response<>(null,OpCode.Not_Login);
    }

    @Override
    public void deleteReceivedNotifications(List<Integer> notificationsId) {

    }

    @Override
    public Boolean sendMyNotifications() {
        return false;
    }
}
