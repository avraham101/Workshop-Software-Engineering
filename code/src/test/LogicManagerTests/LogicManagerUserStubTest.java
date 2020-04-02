package LogicManagerTests;

import Data.Data;
import DataAPI.StoreData;
import Domain.Product;
import Domain.Review;
import Domain.Store;
import org.junit.Before;

import java.util.List;

import static org.junit.Assert.*;

public class LogicManagerUserStubTest extends LogicManagerUserAndStoresStubs {

    @Before
    public void setUp() {
        super.setUp();
    }

    /**
     * test use case 3.2 - Open Store
     */
    @Override
    protected void testOpenStore() {
        super.testOpenStore();
        StoreData storeData = data.getStore(Data.VALID);
        Store store = stores.get(storeData.getName());
        //This test check if store added
        assertNotNull(store);
        assertEquals(storeData.getName(),store.getName());
        //This test check if can add store twiced
        assertFalse(logicManager.openStore(data.getStore(Data.VALID)));
    }

    /**
     * part of test use case 3.2 - Open Store
     */
    @Override
    protected void testOpenStoreSucces(){
        StoreData storeData = data.getStore(Data.VALID);
        assertTrue(logicManager.openStore(storeData));
    }

    /**
     * part of use case 3.3 - write review
     */
    @Override
    protected void testWriteReviewValid() {
        super.testWriteReviewValid();
        Review review = data.getReview(Data.VALID);
        //check if the review is in store
        Store store = stores.get(review.getStore());
        Product p = store.getProduct(review.getProductName());
        //check if the review is in the product
        List<Review> reviewList = p.getReviews();
        assertEquals(1,reviewList.size());
        Review result = reviewList.get(0);
        assertEquals(review.getContent(), result.getContent());
        assertEquals(review.getWriter(), result.getWriter());

    }

}
