package Domain;

import DataAPI.*;
import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Guest extends UserState {

    public Guest() {
        super();
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
        this.cart = new Cart();
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
    public Request addRequest(int requestId, String storeName, String content){
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

    /**
     * use case 4.7 - remove manager
     * @param userName
     * @param storeName
     * @return
     */
    @Override
    public Response<Boolean>  removeManager(String userName, String storeName) {
        return new Response<>(false,OpCode.Not_Login);
    }

    /**
     * use case 4.9.1 - view request
     * @param storeName
     * @return
     */
    @Override
    public List<Request> viewRequest(String storeName) {
        return new LinkedList<>();
    }

    /**
     * use case 4.9.2 - reply request
     * @param storeName
     * @param requestID
     * @param content
     * @return
     */
    @Override
    public Response<Request> replayToRequest(String storeName, int requestID, String content) {
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
}
