package Domain;

import DataAPI.ProductData;
import DataAPI.StoreData;
import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Subscribe extends UserState{

    private String userName; //unique
    private String password;
    private HashMap<String, Permission> permissions; //map of <storeName, Domain.Permission>
    private HashMap<String,Permission> givenByMePermissions; //map of <storeName, Domain.Permission>
    private List<Purchase> purchases;
    private List<Request> requests;

    public Subscribe(String userName, String password) {
        this.userName = userName;
        this.password = password;
        permissions=new HashMap<>();
        givenByMePermissions=new HashMap<>();
        purchases=new ArrayList<>();
        requests=new ArrayList<>();
    }

    /**
     * use case 2.3 - Login
     * @param user - The user who using the system
     * @param subscribe - The user state who need to be set
     * @return always false. The user state cant be changed from subscribe -> subscribe.
     *         the user already logged
     */
    @Override
    public boolean login(User user, Subscribe subscribe) {
        return false;
    }

    /**
     * use case 3.1 - Logout
     * @param user - the user who using the system
     * @return return true. The user changed his state to guest from subscribe.
     */
    @Override
    public boolean logout(User user) {
        user.setState(new Guest());
        return true;
    }

    @Override
    public String getName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    /**
     * use case 3.2
     * @param storeDetails - the details of the store
     * @param paymentSystem - The external payment System
     * @param supplySystem - The external supply System
     * @return The store that we opened.
     */
    @Override
    public Store openStore(StoreData storeDetails, PaymentSystem paymentSystem, SupplySystem supplySystem) {
        Permission permission = new Permission(this);
        Store store = new Store(storeDetails.getName(),storeDetails.getPurchesPolicy(),
                storeDetails.getDiscountPolicy(), permission, supplySystem, paymentSystem);
        permission.setStore(store);
        permission.addType(PermissionType.OWNER); //Always true, store just created.
        permissions.put(store.getName(),permission);
        return store;
    }

    /**
     * use case 4.9.1
     * @param productData product details to be added
     * @return if the product was added
     */

    @Override
    public boolean addProductToStore(ProductData productData) {
        if(!permissions.containsKey(productData.getStoreName()))
            return false;
        Permission permission=permissions.get(productData.getStoreName());
        if(!permission.canAddProduct())
            return false;
        return permission.getStore().addProduct(productData);
    }

    /**
     * use case 4.9.2
     * @param storeName name of store to remove the product from
     * @param productName name of the product to be removed
     * @return if the product was removed
     */
    @Override
    public boolean removeProductFromStore(String storeName, String productName) {
        if(!permissions.containsKey(storeName))
            return false;
        if(!permissions.get(storeName).canAddProduct())
            return false;
        return permissions.get(storeName).getStore().removeProduct(productName);
    }

    /**
     *
     * @param productData product to be added
     * @return
     */
    @Override
    public boolean editProductFromStore(ProductData productData) {
        if(!permissions.containsKey(productData.getStoreName()))
            return false;
        if(!permissions.get(productData.getStoreName()).canAddProduct())
            return false;
        return permissions.get(productData.getStoreName()).getStore().editProduct(productData);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashMap<String, Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(HashMap<String, Permission> permissions) {
        this.permissions = permissions;
    }

    public HashMap<String, Permission> getGivenByMePermissions() {
        return givenByMePermissions;
    }

    public void setGivenByMePermissions(HashMap<String, Permission> givenByMePermissions) {
        this.givenByMePermissions = givenByMePermissions;
    }

    public List<Purchase> getPurchese() {
        return purchases;
    }

    public void setPurchese(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

}
