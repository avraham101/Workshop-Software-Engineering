package Domain;

import DataAPI.ProductData;
import DataAPI.StoreData;
import Systems.HashSystem;
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

    @Override
    public boolean login(User user, Subscribe subscribe) {
        return false;
    }

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
     * @return open a store.
     */
    @Override
    public Store openStore(StoreData storeDetails, PaymentSystem paymentSystem, SupplySystem supplySystem) {
        Permission permission = new Permission(this);
        Store store = new Store(storeDetails.getName(),storeDetails.getPurchesPolicy(),
                storeDetails.getDiscountPolicy(), permission, supplySystem, paymentSystem);
        permission.setStore(store);
        permission.addType(PermissionType.OWNER); //Always true, store just created.
        return store;
    }


    @Override
    public boolean addProductToStore(ProductData productData) {
        if(!permissions.containsKey(productData.getStoreName()))
            return false;
        Permission permission=permissions.get(productData.getStoreName());
        if(!permission.canAddProduct())
            return false;
        return permission.getStore().addProduct(productData);
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
