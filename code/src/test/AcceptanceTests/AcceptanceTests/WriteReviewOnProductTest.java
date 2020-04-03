package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.ProductTestData;
import AcceptanceTests.AcceptanceTestDataObjects.PurchaseTestData;
import AcceptanceTests.AcceptanceTestDataObjects.ReviewTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class WriteReviewOnProductTest extends AcceptanceTests{
    private UserTestData user0;
    private PurchaseTestData purchase0;
    private HashMap<ProductTestData,ReviewTestData> productsAndReviews;
    private ProductTestData notExistingProductInPurchase;

    @Test
    public void setUp(){
        super.setUp();
        user0 = users.get(0);
        purchase0 = user0.getPurchases().get(0);
        notExistingProductInPurchase = products.get(7);

        bridge.register(user0.getUsername(),user0.getPassword());
        bridge.login(user0.getUsername(),user0.getPassword());
        bridge.purchaseProducts(purchase0);

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
            String storeName = product.getStoreName();
            ReviewTestData review = entry.getValue();
            boolean isWritten = bridge.writeReviewOnProduct(storeName,product,review);
            assertTrue(isWritten);

            ReviewTestData actualReview = bridge.getReviewByProductAndDate(purchase0.getPurchaseDate(),product);
            assertEquals(review,actualReview);
        }
    }

    @Test
    public void writeReviewOnProductTestFailLogoutUser(){
        boolean isLoggedOut = bridge.logout(user0.getUsername());
        assertTrue(isLoggedOut);
        writeReviewOnProductTestSuccess();
    }

    @Test
    public void writeReviewOnProductTestFailNotExistingStore(){
        ProductTestData productToReview = products.get(0);
        ReviewTestData review = productsAndReviews.get(productToReview);
        String storeName = notExistingStore.getStoreName();

        boolean isWritten = bridge.writeReviewOnProduct(storeName, productToReview, review);
        assertFalse(isWritten);
    }

    @Test
    public void writeReviewOnProductTestFailNotExistingProductInPurchase(){
        ProductTestData productToReview = notExistingProductInPurchase;
        ReviewTestData review = productsAndReviews.get(productToReview);
        String storeName = notExistingProductInPurchase.getStoreName();

        boolean isWritten = bridge.writeReviewOnProduct(storeName, productToReview, review);
        assertFalse(isWritten);
    }
}
