package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.*;
import org.junit.Test;
import static org.junit.Assert.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WriteReviewOnProductTest extends AcceptanceTests{
    private UserTestData user0;
    private HashMap<ProductTestData,ReviewTestData> productsAndReviews;
    private ProductTestData notExistingProductInPurchase;

    @Test
    public void setUp(){
        user0 = superUser;
        addUserStoresAndProducts(user0);

        addCartToUser(superUser.getCart());
        notExistingProductInPurchase = products.get(7);
        setUpTestProductsReviews();
    }


    private void setUpTestProductsReviews(){
        productsAndReviews = new HashMap<>();
        ReviewTestData review0 = new ReviewTestData(user0.getUsername(),"review0test");
        ReviewTestData review4 = new ReviewTestData(user0.getUsername(),"review4test");
        ProductTestData product0 = products.get(0);
        ProductTestData product4 = products.get(4);
        productsAndReviews.put(product0,review0);
        productsAndReviews.put(product4,review4);
    }

    @Test
    public void writeReviewOnProductTestSuccess(){

        for(Map.Entry<ProductTestData,ReviewTestData> entry : productsAndReviews.entrySet()){
            ProductTestData product = entry.getKey();
            ReviewTestData review = entry.getValue();
            boolean isWritten = bridge.writeReviewOnProduct(product,review);
            assertTrue(isWritten);

            List<ReviewTestData> actualReviews = bridge.getProductsReviews(product);
            assertTrue(actualReviews.contains(review));
        }
    }

    @Test
    public void writeReviewOnProductTestFailLogoutUser(){
        boolean isLoggedOut = bridge.logout();
        assertTrue(isLoggedOut);

        for(Map.Entry<ProductTestData,ReviewTestData> entry : productsAndReviews.entrySet()){
            ProductTestData product = entry.getKey();
            ReviewTestData review = entry.getValue();
            boolean isWritten = bridge.writeReviewOnProduct(product,review);
            assertTrue(isWritten);

            List<ReviewTestData> actualReviews = bridge.getProductsReviews(product);
            assertFalse(actualReviews.contains(review));
        }
    }

    @Test
    public void writeReviewOnProductTestFailNotExistingStore(){
        ProductTestData productToReview = products.get(0);
        ReviewTestData review = productsAndReviews.get(productToReview);
        productToReview.setStoreName(notExistingStore.getStoreName());

        boolean isWritten = bridge.writeReviewOnProduct(productToReview, review);
        assertFalse(isWritten);

        List<ReviewTestData> actualReviews = bridge.getProductsReviews(productToReview);
        assertFalse(actualReviews.contains(review));
    }

    @Test
    public void writeReviewOnProductTestFailNotExistingProductInPurchase(){
        ProductTestData productToReview = notExistingProductInPurchase;
        ReviewTestData review = productsAndReviews.get(productToReview);

        boolean isWritten = bridge.writeReviewOnProduct(productToReview, review);
        assertFalse(isWritten);

        List<ReviewTestData> actualReviews = bridge.getProductsReviews(productToReview);
        assertFalse(actualReviews.contains(review));
    }

}
