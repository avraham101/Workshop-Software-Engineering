package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.ProductTestData;
import AcceptanceTests.AcceptanceTestDataObjects.ReviewTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * use case 3.3 - write review
 */

public class WriteReviewOnProductTest extends AcceptanceTests{
    private UserTestData user0;
    private HashMap<ProductTestData,ReviewTestData> productsAndReviews;
    private ProductTestData notExistingProductInPurchase;
    private ReviewTestData notExistingProductInPurchaseReview;

    @Before
    public void setUp(){
        user0 = superUser;
        addUserStoresAndProducts(user0);

        addCartToUser(superUser.getId(),superUser.getCart());
        bridge.buyCart(superUser.getId(),validPayment,validDelivery);
        notExistingProductInPurchase = products.get(7);
        notExistingProductInPurchaseReview = new ReviewTestData(user0.getUsername(),"notExistingProductInPurchaseReview");
        setUpTestProductsReviews();
    }


    private void setUpTestProductsReviews(){
        productsAndReviews = new HashMap<>();
        ReviewTestData review0 = new ReviewTestData(user0.getUsername(),"review0test");
        ReviewTestData review4 = new ReviewTestData(user0.getUsername(),"review4test");
        ProductTestData product0 = products.get(0);
        ProductTestData product4 = products.get(1);
        productsAndReviews.put(product0,review0);
        productsAndReviews.put(product4,review4);
    }

    @Test
    public void writeReviewOnProductTestSuccess(){

        for(Map.Entry<ProductTestData,ReviewTestData> entry : productsAndReviews.entrySet()){
            ProductTestData product = entry.getKey();
            ReviewTestData review = entry.getValue();
            boolean isWritten = bridge.writeReviewOnProduct(user0.getId(),product,review);
            assertTrue(isWritten);

            List<ReviewTestData> actualReviews = bridge.getProductsReviews(product);
            assertTrue(actualReviews.contains(review));
        }
    }

    @Test
    public void writeReviewOnProductTestFailLogoutUser(){
        boolean isLoggedOut = bridge.logout(user0.getId());
        assertTrue(isLoggedOut);

        for(Map.Entry<ProductTestData,ReviewTestData> entry : productsAndReviews.entrySet()){
            ProductTestData product = entry.getKey();
            ReviewTestData review = entry.getValue();
            boolean isWritten = bridge.writeReviewOnProduct(user0.getId(),product,review);
            assertFalse(isWritten);

            List<ReviewTestData> actualReviews = bridge.getProductsReviews(product);
            assertFalse(actualReviews.contains(review));
        }
    }

    @Test
    public void writeReviewOnProductTestFailNotExistingStore(){
        ProductTestData productToReview = products.get(0);
        ReviewTestData review = productsAndReviews.get(productToReview);
        productToReview.setStoreName(notExistingStore.getStoreName());

        boolean isWritten = bridge.writeReviewOnProduct(user0.getId(),productToReview, review);
        assertFalse(isWritten);

        List<ReviewTestData> actualReviews = bridge.getProductsReviews(productToReview);
        assertNull(actualReviews);
    }

    @Test
    public void writeReviewOnProductTestFailNotExistingProductInPurchase(){
        ProductTestData productToReview = notExistingProductInPurchase;
        ReviewTestData review = notExistingProductInPurchaseReview;

        boolean isWritten = bridge.writeReviewOnProduct(user0.getId(),productToReview, review);
        assertFalse(isWritten);

        List<ReviewTestData> actualReviews = bridge.getProductsReviews(productToReview);
        assertFalse(actualReviews.contains(review));
    }

    @After
    public void tearDown(){
        removeProducts(products);
        removeStores(stores);
    }
}
