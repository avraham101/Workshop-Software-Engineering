package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.ProductTestData;
import AcceptanceTests.AcceptanceTestDataObjects.StoreTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EditStore extends AcceptanceTests {
    @Before
    public void setUp(){
        addUserStoresAndProducts(superUser);
    }
    /****************ADD-PRODUCT-4.1.1*************************************/
    @Test
    public void addProductToStoreSuccess(){
        String storeName = stores.get(0).getStoreName();
        ProductTestData product = new ProductTestData("newProduct",
                storeName,5,15.5,"tests",null);
        boolean approval = bridge.addProduct(product);
        assertTrue(approval);

        StoreTestData store = bridge.getStoreInfoByName(storeName);
        ProductTestData prod = store.getProductByName("newProduct");
        assertEquals(prod ,product);
    }

    @Test
    public void addProductToStoreFailProductExist(){
        boolean approval = bridge.addProduct(stores.get(0).getProducts().get(0));
        assertFalse(approval);
    }

    @Test
    public void addProductToStoreFailInvalidAmount(){
        ProductTestData product = new ProductTestData("newProduct",
                "store0Test",-1,15.5,"tests",null);
        boolean approval = bridge.addProduct(product);
        assertFalse(approval);
    }

    @Test
    public void addProductFailInvalidPrice(){
        ProductTestData product = new ProductTestData("newProduct",
                "store0Test",5,-1,"tests",null);
        boolean approval = bridge.addProduct(product);
        assertFalse(approval);
    }

    @Test
    public void addProductFailNotMyStore(){
        ProductTestData product = new ProductTestData("newProduct",
                "store2Test",5,15.5,"tests",null);
        boolean approval = bridge.addProduct(product);
        assertFalse(approval);
    }

    /****************DELETE-PRODUCT-4.1.2****************************************/
    @Test
    public void deleteProductSuccess(){
        ProductTestData product = stores.get(0).getProducts().get(0);
        boolean approval = bridge.deleteProduct(product);
        assertTrue(approval);
        StoreTestData store = bridge.getStoreInfoByName(stores.get(0).getStoreName());
        assertNull(store.getProductByName(product.getProductName()));

    }
    @Test
    public void deleteProductNotExist() {
        ProductTestData product = new ProductTestData("newProduct",
                "store0Test", 5, 15.5, "tests", null);
        boolean approval = bridge.deleteProduct(product);
        assertFalse(approval);
    }

    @Test
    public void deleteProductFailNotMyStore(){
        ProductTestData product = stores.get(2).getProducts().get(0);
        boolean approval = bridge.addProduct(product);
        assertFalse(approval);

    }
    /****************EDIT-PRODUCT-4.1.3*************************************/
    @Test
    public void editProductSuccess(){
        ProductTestData product = stores.get(0).getProducts().get(0);
        product.setAmountInStore(10);
        product.setPrice(100);
        product.setCategory("newCategory");
        boolean approval = bridge.editProductInStore(product);
        assertTrue(approval);
        StoreTestData store = bridge.getStoreInfoByName( stores.get(0).getStoreName());
        ProductTestData newProduct = store.getProductByName(product.getProductName());
        assertEquals(product.getPrice(),newProduct.getPrice());
        assertEquals(product.getAmountInStore(),newProduct.getAmountInStore());
        assertEquals(product.getCategory(),newProduct.getCategory());
    }

    @Test
    public void editProductFailAlreadyExistName(){
        ProductTestData product = stores.get(0).getProducts().get(0);
        product.setProductName(stores.get(0).getProducts().get(0).getProductName());
        boolean approval = bridge.editProductInStore(product);
        assertFalse(approval);
    }

    @Test
    public void editProductFailInvalidAmount(){
        ProductTestData product = stores.get(0).getProducts().get(0);
        product.setAmountInStore(-1);
        boolean approval = bridge.editProductInStore(product);
        assertFalse(approval);
    }
    @Test
    public void editProductFailInvalidPrice(){
        ProductTestData product = stores.get(0).getProducts().get(0);
        product.setPrice(-1);
        boolean approval = bridge.editProductInStore(product);
        assertFalse(approval);
    }
    @Test
    public void editProductFailNotMyStore(){
        ProductTestData product = stores.get(2).getProducts().get(0);
        boolean approval = bridge.editProductInStore(product);
        assertFalse(approval);
    }

}
