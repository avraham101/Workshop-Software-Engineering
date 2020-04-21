package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.*;
import Domain.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * use case 4.3 - appoint owner
 */

public class AppointAnotherOwnerToStoreTest extends AcceptanceTests {
    private UserTestData owner;
    private UserTestData newOwner;
    private StoreTestData shareOwnershipStore;
    private ProductTestData productToAdd;

    @Before
    public void setUp(){
        owner = superUser;
        newOwner = users.get(1);
        shareOwnershipStore = stores.get(0);
        bridge.register(owner.getUsername(),owner.getPassword());
        bridge.register(newOwner.getUsername(),newOwner.getPassword());
        bridge.login(owner.getUsername(),owner.getPassword());
        addStores(stores);
        addProducts(products);
        productToAdd = new ProductTestData("newProductTest",
                                                            shareOwnershipStore.getStoreName(),
                                                            100,
                                                            4,
                                                            "Dairy",
                                                            new ArrayList<ReviewTestData>(),
                                                            new ArrayList<DiscountTestData>());
    }

    @Test
    public void appointAnotherOwnerToStoreTestSuccess(){
        boolean isOwner = bridge.appointOwnerToStore(shareOwnershipStore.getStoreName(),newOwner.getUsername());
        assertTrue(isOwner);
        isOwner = bridge.addProduct(productToAdd);
        assertTrue(isOwner);
    }

    @Test
    public void appointAnotherOwnerToStoreTestFailAlreadyOwner(){
        appointAnotherOwnerToStoreTestSuccess();
        boolean isOwner = bridge.appointOwnerToStore(shareOwnershipStore.getStoreName(),newOwner.getUsername());
        assertFalse(isOwner);
        logoutAndLogin(newOwner);
        bridge.deleteProduct(productToAdd);
        isOwner = bridge.addProduct(productToAdd);
        assertTrue(isOwner);
    }

    @Test
    public void appointAnotherOwnerToStoreTestFailWrongUsername(){
        String wrongUsername = newOwner.getUsername() + newOwner.getUsername();
        boolean isOwner = bridge.appointOwnerToStore(shareOwnershipStore.getStoreName(),wrongUsername);
        assertFalse(isOwner);
        logoutAndLogin(new UserTestData(wrongUsername,wrongUsername));
        isOwner = bridge.addProduct(productToAdd);
        assertFalse(isOwner);
    }

    @Test
    public void appointAnotherOwnerToStoreTestFailWrongStoreName(){
        String wrongStoreName = shareOwnershipStore.getStoreName() + shareOwnershipStore.getStoreName();
        boolean isOwner = bridge.appointOwnerToStore(wrongStoreName,newOwner.getUsername());
        assertFalse(isOwner);
        logoutAndLogin(newOwner);
        productToAdd.setStoreName(wrongStoreName);
        isOwner = bridge.addProduct(productToAdd);
        assertFalse(isOwner);
    }
}
