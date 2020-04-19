package Store;

import Data.Data;
import DataAPI.ProductData;
import static org.junit.Assert.*;

public class StoreTestReal extends StoreTestsAllStubs {

    /**
     * use case 4.1.1
     */
    @Override
    protected void testAddProductSuccess() {
        super.testAddProductSuccess();
        ProductData p=data.getProductData(Data.VALID);
        assertTrue(store.getProducts().get(p.getProductName()).equal(p));
    }

    @Override
    protected void testSuccessRemoveProduct() {
        super.testSuccessRemoveProduct();
        assertFalse(store.getProducts().containsKey(data.getProductData(Data.VALID).getProductName()));
    }

    /**
     * use case 4.1.3
     */
    @Override
    protected void testSuccessEditProduct() {
        super.testSuccessEditProduct();
        ProductData product=data.getProductData(Data.EDIT);
        assertTrue(store.getProducts().get(product.getProductName()).equal(product));
    }
}
