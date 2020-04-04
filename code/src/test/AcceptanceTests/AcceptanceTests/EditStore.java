package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.ProductTestData;
import AcceptanceTests.AcceptanceTestDataObjects.StoreTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class EditStore extends AcceptanceTests {
    @Before
    public void setUp(){
        super.setUp();
        bridge.login(superUser.getUsername(),superUser.getPassword());
        addStores(stores);
        addProducts(products);
    }
    /****************ADD-PRODUCT-4.1.1*************************************/
    @Test
    public void addProductToStoreSuccess(){
        ProductTestData product = new ProductTestData("newProduct",
                "store0Test",5,15.5,"tests",null);
        boolean approval=bridge.addProduct(product);
        assertTrue(approval);
        StoreTestData store = bridge.getStoreInfoByName("store0Test");
        ProductTestData prod=store.getProductByName("newProduct");
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
        //TODO check product not in store??

    }

    @Test
    public void addProductFailInvalidPrice(){
        ProductTestData product = new ProductTestData("newProduct",
                "store0Test",5,-1,"tests",null);
        boolean approval = bridge.addProduct(product);
        assertFalse(approval);

    }
    //TODO add not my store fail??
    @Test
    public void addProductFailNotMyStore(){

    }

    /****************DELETE-PRODUCT-4.1.2****************************************/
    @Test
    public void deleteProductSuccess(){
        ProductTestData product = stores.get(0).getProducts().get(0);
        boolean approval=bridge.deleteProduct(product);
        assertTrue(approval);
        StoreTestData store=bridge.getStoreInfoByName(stores.get(0).getStoreName());
        assertNull(store.getProductByName(product.getProductName()));

    }
    @Test
    public void deleteProductNotExist() {
        ProductTestData product = new ProductTestData("newProduct",
                "store0Test", 5, 15.5, "tests", null);
        boolean approval = bridge.deleteProduct(product);
        assertFalse(approval);
    }

    //TODO add not my store fail??
    /****************EDIT-PRODUCT-4.1.3*************************************/
    @Test
    public void editProductSuccess(){
        ProductTestData product = new ProductTestData("newProduct",
                "store0Test", 5, 15.5, "tests", null);


    }





    @After
    public void tearDown(){
        bridge.logout(superUser.getUsername());
    }
}
