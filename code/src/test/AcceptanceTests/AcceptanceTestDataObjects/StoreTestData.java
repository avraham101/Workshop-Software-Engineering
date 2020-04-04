package AcceptanceTests.AcceptanceTestDataObjects;

import java.util.List;

public class StoreTestData {
    private String storeName;
    private UserTestData storeManager;
    private List<ProductTestData> products;

    //TODO: change storeManager to storeOwner
    //TODO: add a list of store managers

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
