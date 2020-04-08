package AcceptanceTests.AcceptanceTestsBridge;

import AcceptanceTests.AcceptanceTestDataObjects.*;
import AcceptanceTests.AcceptanceTestDataObjects.FilterTestData.FilterTestData;
import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import DataAPI.ProductData;
import DataAPI.StoreData;
import Domain.Discount;
import Domain.Purchase;
import Domain.Review;
import Service.ServiceAPI;

import java.util.*;

public class AcceptanceTestsRealBridge implements AcceptanceTestsBridge {
    private ServiceAPI serviceAPI;
    private UserTestData currentUser;

    @Override
    public boolean initialStart(String username, String password) {
        try{
            this.serviceAPI = new ServiceAPI(username,password);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public String getAdminUsername() {
        return null;
    }

    @Override
    public void resetSystem() {
        try {
            this.serviceAPI = new ServiceAPI("admin", "admin");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean register(String username, String password) {
        return serviceAPI.register(username,password);
    }

    @Override
    public String getCurrentLoggedInUser() {
        if(currentUser!=null)
            return currentUser.getUsername();
        else
            return null;
    }

    @Override
    public boolean logout() {
        return false;
    }

    @Override
    public boolean login(String username, String password) {
        if(serviceAPI.login(username,password)){
            currentUser = new UserTestData(username,password);
            return true;
        }
        return false;
    }

    @Override
    public HashSet<ProductTestData> filterProducts(List<FilterTestData> filters) {
        return null;
    }


    @Override
    public void addStores(List<StoreTestData> stores) {

    }

    @Override
    public void addProducts(List<ProductTestData> products) {

    }


    @Override
    public CartTestData getCurrentUsersCart() {
        return null;
    }

    @Override
    public boolean deleteFromCurrentUserCart(BasketTestData basketToDeleteFrom, ProductTestData productToDelete) {
        return false;
    }

    @Override
    public boolean changeCurrentUserAmountOfProductInCart(BasketTestData basketToChangeAmountIn, ProductTestData productToChangeAmount, int newAmount) {
        return false;
    }

    @Override
    public boolean addToCurrentUserCart(ProductTestData productToAdd, int amount) {
        return false;
    }

    @Override
    public boolean writeReviewOnProduct(ProductTestData product, ReviewTestData review) {
        return false;
    }

    @Override
    public ReviewTestData getReviewByProductAndDate(String purchaseDate, ProductTestData product) {
        return null;
    }

    @Override
    public List<StoreTestData> getStoresInfo() {
        List<StoreData> storeData = serviceAPI.viewStores();
        return buildStoresTestData(storeData);
    }

    private List<StoreTestData> buildStoresTestData(List<StoreData> storeData){
        List<StoreTestData> storeTestData = new ArrayList<>();
        for(StoreData sd : storeData)
            storeTestData.add(buildStoreTestData(sd));
        return storeTestData;
    }

    private StoreTestData buildStoreTestData(StoreData storeData) {
        String storeName = storeData.getName();
        List<ProductData> storeProducts  = serviceAPI.viewProductsInStore(storeName);
        List<ProductTestData> storeTestProducts = buildProductsTestData(storeProducts);
        StoreTestData storeToReturn = new StoreTestData(storeName,null);
        storeToReturn.setProducts(storeTestProducts);
        return storeToReturn;
    }

    private List<ProductTestData> buildProductsTestData(List<ProductData> storeProducts) {
        List<ProductTestData> productsTestData = new ArrayList<>();
        for(ProductData pd : storeProducts)
            productsTestData.add(buildProductTestData(pd));

        return productsTestData;
    }

    private ProductTestData buildProductTestData(ProductData productData) {
        String storeName = productData.getStoreName();
        String productName = productData.getProductName();
        int amountInStore = productData.getAmount();
        double price = productData.getPrice();
        String category = productData.getCategory();
        List<DiscountTestData> discounts = buildDiscountsTestData(productData.getDiscount());
        List<ReviewTestData> reviews = buildReviewsTestData(productData.getReviews());

        return new ProductTestData(productName,storeName,amountInStore,price,category,reviews,discounts);
    }

    private List<ReviewTestData> buildReviewsTestData(List<Review> reviews) {
        List<ReviewTestData> reviewsTestData  = new ArrayList<>();
        for(Review review : reviews)
            reviewsTestData.add(buildReviewTestData(review));
        return reviewsTestData;
    }

    private ReviewTestData buildReviewTestData(Review review) {
        return new ReviewTestData(review.getWriter(),review.getContent());
    }

    private List<DiscountTestData> buildDiscountsTestData(List<Discount> discounts) {
        List<DiscountTestData> discountsTestData = new ArrayList<>();
        for(Discount discount : discounts )
            discountsTestData.add(buildDiscountTestData(discount));

        return discountsTestData;
    }

    private DiscountTestData buildDiscountTestData(Discount discount) {
        return new DiscountTestData(discount.getPercentage(),null);
    }


    @Override
    public List<ProductTestData> getStoreProducts(String storeName) {
        return null;
    }

    @Override
    public PurchaseTestData buyCart(PaymentTestData paymentMethod, DeliveryDetailsTestData deliveryDetails) {
        PaymentData paymentData = new PaymentData(paymentMethod.getCreditCardOwner()
                ,"address",paymentMethod.getCreditCardNumber() );//TODO: SHOW ROY
        boolean approval = serviceAPI.purchaseCart(paymentData,deliveryDetails.toString());
        if(!approval){
            return null;
        }
        else {
            List<Purchase>  history =  serviceAPI.watchMyPurchaseHistory();
            HashMap<ProductTestData,Integer> productsAndAmountInPurchase = new HashMap<>();
            Date date = new Date();
            for (Purchase purchase: history) {
                 List<ProductData> products = purchase.getProduct();
                for (ProductData product: products) {
                    productsAndAmountInPurchase.put(buildProductTestData(product)
                                                    ,product.getAmount());

                }


            }
            PurchaseTestData purchaseTestData = new PurchaseTestData(productsAndAmountInPurchase,
                    date,)
        }

    }

    @Override
    public void changeAmountOfProductInStore(ProductTestData product, int amount) {

    }

    @Override
    public StoreTestData openStore(String storeName) {
        return null;
    }

    @Override
    public boolean sendApplicationToStore(String storeName, String message) {
        return false;
    }

    @Override
    public boolean addProduct(ProductTestData product) {
        return false;
    }

    @Override
    public boolean deleteProduct(ProductTestData product) {
        return false;
    }

    @Override
    public StoreTestData getStoreInfoByName(String storeName) {
        return null;
    }

    @Override
    public boolean editProductInStore(ProductTestData product) {
        return false;
    }

    @Override
    public boolean appointManager(String storeName, String username) {
        return false;
    }

    @Override
    public boolean deleteManager(String storeName, String username) {
        return false;
    }

    @Override
    public List<PurchaseTestData> getStorePurchasesHistory(String storeName) {
        return null;
    }

    @Override
    public List<PurchaseTestData> getUserPurchaseHistory(String username) {
        return null;
    }

    @Override
    public boolean appointOwnerToStore(String storeName, String username) {
        return false;
    }

    @Override
    public boolean addPermissionToManager(String storeName, String username, PermissionsTypeTestData productsInventory) {
        return false;
    }

    @Override
    public boolean deletePermission(String storeName, String username, PermissionsTypeTestData productsInventory) {
        return false;
    }

    @Override
    public boolean writeReplyToApplication(String storeName, ApplicationToStoreTestData key, String value) {
        return false;
    }

    @Override
    public HashSet<ApplicationToStoreTestData> viewApplicationToStore(String storeName) {
        return null;
    }

}
