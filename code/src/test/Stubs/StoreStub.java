package Stubs;

import DataAPI.*;
import Domain.*;
import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;

import java.util.HashMap;

public class StoreStub extends Store {

    public StoreStub(String name, PurchasePolicy purchasePolicy, DiscountPolicy discount, Permission permissions) {
        super(name, purchasePolicy, discount, permissions);
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
    public Response<Boolean> addProduct(ProductData productData) {
        return new Response<>(true, OpCode.Success);
    }

    /**
     * use case 4.1.2 - remove product from store
     * @param productName
     * @return
     */
    @Override
    public Response<Boolean> removeProduct(String productName) {
        return new Response<>(true,OpCode.Success);
    }

    /**
     * use case 4.1.3 edit product in store
     * @param productData details of product to edit in store
     * @return
     */
    @Override
    public Response<Boolean> editProduct(ProductData productData) {
        return new Response<>(true,OpCode.Success);
    }
}
