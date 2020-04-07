package Store;

import Data.Data;
import DataAPI.ProductData;
import static org.junit.Assert.*;

public class StoreTestReal extends StoreTestsAllStubs {

    @Override
    protected void makeProductStub() {
    }

    @Override
    protected void testAddProductSuccess() {
        super.testAddProductSuccess();
        ProductData p=data.getProductData(Data.VALID);
        assertTrue(store.getProducts().get(p.getProductName()).equal(p));
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
