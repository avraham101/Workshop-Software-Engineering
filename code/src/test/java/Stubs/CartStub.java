package Stubs;

import DataAPI.*;
import Domain.Cart;
import Domain.Product;
import Domain.Store;

import java.util.List;

public class CartStub extends Cart {

    public CartStub(String buyer) {
        super(buyer);
    }

    /**
     * use case 2.7 - add product
     * @param store - the store to add to cart
     * @param product - the product to add
     * @param amount - the amount of the prduct
     * @return
     */
    @Override
    public boolean addProduct(Store store, Product product, int amount) {
        return true;
    }

    /**
     * use case - 2.8 buy cart
     * the function buy this cart
     * @param paymentData - the payment info
     * @param addresToDeliver  - the address to shift
     * @return
     */
    @Override
    public Response<Boolean> buy(PaymentData paymentData, DeliveryData addresToDeliver) {
        return new Response<>(true, OpCode.Success);
    }

    /**
     * use case 2.8 - buy cart
     * @return
     */
    @Override
    public boolean reserveCart() {
        return false;
    }


    /**
     * use case 2.8 - buy cart
     * @return
     */
    @Override
    public void cancel() {

    }


    /**
     * use case 2.8 - buy cart
     * @return
     */
    @Override
    public List<Purchase> savePurchases(String buyer) {
        return null;
    }
}
