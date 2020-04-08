package AcceptanceTests.AcceptanceTestDataObjects;

import java.util.*;

public class StoreTestData {
    private String storeName;
    private UserTestData storeOwner;
    private List<ProductTestData> products;
    private List<PurchaseTestData> purchasesHistory;
    private HashSet<ApplicationToStoreTestData> applications;

    public StoreTestData(String storeName, UserTestData storeOwner) {
        this.storeName = storeName;
        this.storeOwner = storeOwner;
        this.products = new ArrayList<>();
        this.purchasesHistory = new ArrayList<>();
        this.applications = new HashSet<>();

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

    public UserTestData getStoreOwner() {
        return storeOwner;
    }

    public void setStoreOwner(UserTestData storeOwner) {
        this.storeOwner = storeOwner;
    }

    public List<ProductTestData> getProducts() {
        return products;
    }

    public void setProducts(List<ProductTestData> products) {
        this.products = products;
    }

    public HashSet<ApplicationToStoreTestData> getApplications() {
        return applications;
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

}
