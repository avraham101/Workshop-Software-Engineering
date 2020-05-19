package Stubs;

import DataAPI.OpCode;
import DataAPI.PaymentData;
import DataAPI.ProductData;
import DataAPI.Response;
import Domain.Discount.Discount;
import Domain.Permission;
import Domain.ProductInCart;
import Domain.Review;
import Domain.Store;

import java.util.HashMap;

public class StoreStub extends Store {

    public StoreStub(String name, Permission permissions,String descrption) {
        super(name,permissions,descrption);
    }

    /**
     * use case 2.8 - buy cart
     * policy check
     * @param paymentData - the payment data
     * @param country - the country of the delivery
     * @param list - the products in the basket
     * @return
     */
    @Override
    public boolean policyCheck(PaymentData paymentData, String country, HashMap<String, ProductInCart> list) {
        return false;
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

    /**
     * use case 4.2.1.1
     * @param discount - discount to add to the store
     * @return
     */
    @Override
    public Response<Boolean> addDiscount(Discount discount) {
        return new Response<>(true,OpCode.Success);
    }

    /**
     * use case 4.2.1.2
     * @param discountId - discount to delete from the store
     * @return
     */
    @Override
    public Response<Boolean> deleteDiscount(int discountId) {
        return new Response<>(true,OpCode.Success);
    }
}
