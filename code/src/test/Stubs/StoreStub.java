package Stubs;

import DataAPI.ProductData;
import Domain.*;

public class StoreStub extends Store {

    public StoreStub(String name, PurchasePolicy1 purchasePolicy1, DiscountPolicy discount, Permission permissions) {
        super(name, purchasePolicy1, discount, permissions);
    }


    /**
     * use case 3.3 write review
     * @param review - the review
     * @return ture if the review add to store, otherwise false;
     */
    @Override
    public boolean addReview(Review review) {
        return true;
    }

    /**
     * use case 4.1.1 add product to store
     * @param productData details of product to add to store
     * @return
     */
    @Override
    public boolean addProduct(ProductData productData) {
        return true;
    }

    /**
     * use case 4.1.2 - remove product from store
     * @param productName
     * @return
     */
    @Override
    public boolean removeProduct(String productName) {
        return true;
    }

    /**
     * use case 4.1.3 edit product in store
     * @param productData details of product to edit in store
     * @return
     */
    @Override
    public boolean editProduct(ProductData productData) {
        return true;
    }
}
