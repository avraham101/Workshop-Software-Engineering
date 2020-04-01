package AcceptanceTests.AcceptanceTestDataObjects;

import java.util.List;

public class StoreData {
    private String storeName;
    private UserData storeManager;
    private List<ProductData> products;

    public StoreData(String storeName, UserData storeManager, List<ProductData> products) {
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

    public UserData getStoreManager() {
        return storeManager;
    }

    public void setStoreManager(UserData storeManager) {
        this.storeManager = storeManager;
    }

    public List<ProductData> getProducts() {
        return products;
    }

    public void setProducts(List<ProductData> products) {
        this.products = products;
    }
}
