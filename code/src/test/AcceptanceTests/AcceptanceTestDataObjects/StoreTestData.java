package AcceptanceTests.AcceptanceTestDataObjects;

import java.util.*;

public class StoreTestData {
    private String storeName;
    private UserTestData storeManager;
    private List<ProductTestData> products;
    private HashMap<String, PermissionTestData> permissions;
    private List<PurchaseTestData> purchasesHistory;

    //TODO: change storeManager to storeOwner
    //TODO: add a list of store managers

    public StoreTestData(String storeName, UserTestData storeManager) {
        this.storeName = storeName;
        this.storeManager = storeManager;
        this.products = new ArrayList<>();
        this.permissions = new HashMap<>();
        this.purchasesHistory = new ArrayList<>();

        String username = storeManager.getUsername();
        HashSet<PermissionsTypeTestData> initialPermissions = new HashSet<>();
        initialPermissions.add(PermissionsTypeTestData.OWNER);
        permissions.put(username, new PermissionTestData(username, initialPermissions,username));
    }

    public void addPermission(String username, PermissionsTypeTestData permissionToAdd, String givenBy){
        PermissionTestData permissionsOfUser = permissions.get(username);
        HashSet<PermissionsTypeTestData> permissionSet = new HashSet<>();
        permissionSet.add(permissionToAdd);
        if(permissionsOfUser == null)
            permissions.put(username,new PermissionTestData(username,permissionSet,givenBy));
        else
            permissionsOfUser.getPermissions().addAll(permissionSet);
    }

    public HashMap<String, PermissionTestData> getPermissions() {
        return permissions;
    }

    public List<PurchaseTestData> getPurchasesHistory() {
        return purchasesHistory;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public UserTestData getStoreManager() {
        return storeManager;
    }

    public void setStoreManager(UserTestData storeManager) {
        this.storeManager = storeManager;
    }

    public List<ProductTestData> getProducts() {
        return products;
    }

    public void setProducts(List<ProductTestData> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StoreTestData that = (StoreTestData) o;

        return storeName != null ? storeName.equals(that.storeName) : that.storeName == null;
    }

    public ProductTestData getProductByName(String productName){
        for (ProductTestData pd : products) {
            if(pd.getProductName().equals(productName))
                return pd;
        }
        return null;
    }

    public boolean isManager(String username) {
        return permissions.containsKey(username);
    }

}
