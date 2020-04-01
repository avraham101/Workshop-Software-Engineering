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
        ProductData p=data.getProduct(Data.VALID);
        assertTrue(store.getProducts().get(p.getProductName()).equal(p));
    }
}
