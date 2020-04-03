package AcceptanceTests.AcceptanceTestDataObjects;

import java.util.List;

public class StoreTestData {
    private String storeName;
    private UserTestData storeManager;
    private List<ProductTestData> products;

    public StoreTestData(String storeName, UserTestData storeManager, List<ProductTestData> products) {
        this.storeName = storeName;
        this.storeManager = storeManager;
        this.products = products;
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

    public boolean equals (StoreTestData otherStore){
        return (this.storeName.equals(otherStore.storeName));
    }
}
