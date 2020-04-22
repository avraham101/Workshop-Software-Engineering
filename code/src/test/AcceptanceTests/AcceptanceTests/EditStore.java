package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.DiscountTestData;
import AcceptanceTests.AcceptanceTestDataObjects.ProductTestData;
import AcceptanceTests.AcceptanceTestDataObjects.ReviewTestData;
import AcceptanceTests.AcceptanceTestDataObjects.StoreTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EditStore extends AcceptanceTests {

    
    @Before
    public void setUp(){
        addUserStoresAndProducts(superUser);
    }
    /**
     * 4.1.1 - add product
     */
    @Test
    public void addProductToStoreSuccess(){
        String storeName = stores.get(0).getStoreName();
        ProductTestData product = new ProductTestData("newProduct",
                storeName,5,15.5,"tests",
                new ArrayList<ReviewTestData>(),new ArrayList<DiscountTestData>());
        boolean approval = bridge.addProduct(superUser.getId(),product);
        assertTrue(approval);

        List<ProductTestData> products = bridge.getStoreProducts(storeName);
        assertTrue(products.contains(product));
    }

    @Test
    public void addProductToStoreFailProductExist(){
      //  bridge.addProduct(superUser.getId(),stores.get(0).getProducts().get(0));
        boolean approval = bridge.addProduct(superUser.getId(),stores.get(0).getProducts().get(0));
        assertFalse(approval);
    }

    @Test
    public void addProductToStoreFailInvalidAmount(){
        ProductTestData product = new ProductTestData("newProduct",
                "store0Test",-1,15.5,"tests"
                ,new ArrayList<ReviewTestData>(),new ArrayList<DiscountTestData>());
        boolean approval = bridge.addProduct(superUser.getId(),product);
        assertFalse(approval);

        List<ProductTestData> products = bridge.getStoreProducts("store0Test");
        assertFalse(products.contains(product));
    }

    @Test
    public void addProductFailInvalidPrice(){
        ProductTestData product = new ProductTestData("newProduct",
                "store0Test",5,-1,"tests"
                ,new ArrayList<ReviewTestData>(),new ArrayList<DiscountTestData>());
        boolean approval = bridge.addProduct(superUser.getId(),product);
        assertFalse(approval);

        List<ProductTestData> products = bridge.getStoreProducts("store0Test");
        assertFalse(products.contains(product));
    }

    @Test
    public void addProductFailNotMyStore(){
        ProductTestData product = new ProductTestData("newProduct",
                "store2Test",5,15.5,"tests",
                new ArrayList<ReviewTestData>(),new ArrayList<DiscountTestData>());
        boolean approval = bridge.addProduct(superUser.getId(),product);
        assertFalse(approval);

        List<ProductTestData> products = bridge.getStoreProducts("store2Test");
        assertFalse(products.contains(product));
    }
    /**
     * 4.1.2 - delete product
     */

    @Test
    public void deleteProductSuccess(){
        ProductTestData product = stores.get(0).getProducts().get(0);
        bridge.addProduct(superUser.getId(),product);
        boolean approval = bridge.deleteProduct(superUser.getId(),product);
        assertTrue(approval);

        List<ProductTestData> products = bridge.getStoreProducts(stores.get(0).getStoreName());
        assertFalse(products.contains(product));

    }
    @Test
    public void deleteProductNotExist() {
        ProductTestData product = new ProductTestData("newProduct",
                "store0Test", 5, 15.5, "tests",
                new ArrayList<ReviewTestData>(),new ArrayList<DiscountTestData>());
        boolean approval = bridge.deleteProduct(superUser.getId(),product);
        assertFalse(approval);
    }

    @Test
    public void deleteProductFailNotMyStore(){
        ProductTestData product = stores.get(2).getProducts().get(0);
        boolean approval = bridge.addProduct(superUser.getId(),product);
        assertFalse(approval);

    }
    /****************EDIT-PRODUCT-4.1.3*************************************/
    @Test
    public void editProductSuccess(){
        ProductTestData product = stores.get(0).getProducts().get(0);
        product.setAmountInStore(10);
        product.setPrice(100);
        product.setCategory("newCategory");
        boolean approval = bridge.editProductInStore(superUser.getId(),product);
        assertTrue(approval);
        StoreTestData store = bridge.getStoreInfoByName( stores.get(0).getStoreName());
        List<ProductTestData> products = bridge.getStoreProducts(stores.get(0).getStoreName());
        store.setProducts(products);
        ProductTestData newProduct = store.getProductByName(product.getProductName());
        assertEquals(product.getPrice(),newProduct.getPrice(),0.0);
        assertEquals(product.getAmountInStore(),newProduct.getAmountInStore());
        assertEquals(product.getCategory(),newProduct.getCategory());
    }

    @Test
    public void editProductFailInvalidAmount(){
        ProductTestData product = stores.get(0).getProducts().get(0);
        product.setAmountInStore(-1);
        boolean approval = bridge.editProductInStore(superUser.getId(),product);
        assertFalse(approval);
    }
    @Test
    public void editProductFailInvalidPrice(){
        ProductTestData product = stores.get(0).getProducts().get(0);
        product.setPrice(-1);
        boolean approval = bridge.editProductInStore(superUser.getId(),product);
        assertFalse(approval);
    }
    @Test
    public void editProductFailNotMyStore(){
        ProductTestData product = stores.get(2).getProducts().get(0);
        boolean approval = bridge.editProductInStore(superUser.getId(),product);
        assertFalse(approval);
    }


}
