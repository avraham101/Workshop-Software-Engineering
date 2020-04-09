package AcceptanceTests.AcceptanceTestsBridge;

import AcceptanceTests.AcceptanceTestDataObjects.*;
import AcceptanceTests.AcceptanceTestDataObjects.FilterTestData.FilterTestData;
import DataAPI.*;
import Domain.*;
import Service.ServiceAPI;

import java.time.ZoneId;
import java.util.*;

public class AcceptanceTestsRealBridge implements AcceptanceTestsBridge {
    private ServiceAPI serviceAPI;
    private UserTestData currentUser;
    private int requestId = 0; //TODO: where do i get the request id?

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
        Filter productsFilter = buildFilterFromTestFilters(filters);
        List<ProductData> filteredProducts =  serviceAPI.viewSpasificProducts(productsFilter);
        return new HashSet<>(buildProductsTestData(filteredProducts));
    }
    //TODO: ask abraham
    private Filter buildFilterFromTestFilters(List<FilterTestData> filters) {
        Search search = null;
        double minValue = -1, maxValue = -1;
        String category = null, value = null;
        return null;
    }


    @Override
    public void addStores(List<StoreTestData> stores) {

    }

    @Override
    public void addProducts(List<ProductTestData> products) {
        for(ProductTestData productTestData : products){
            ProductData productData = buildProductData(productTestData);
            serviceAPI.addProductToStore(productData);
        }
    }


    @Override
    public CartTestData getCurrentUsersCart() {
        CartData cartData = serviceAPI.watchCartDetatils();
        return buildCartTestData(cartData);
    }

    //TODO: check with abraham what to do with amounts
    private CartTestData buildCartTestData(CartData cartData) {
        HashMap<String,BasketTestData> basketsAndStoreNames = new HashMap<>();
        Set<String> storeNames = new HashSet<>();
        List<ProductTestData> products = buildProductsTestData(cartData.getProducts());

        for(ProductData productData : cartData.getProducts())
            storeNames.add(productData.getStoreName());

        for(String storeName : storeNames)
            basketsAndStoreNames.put(storeName,new BasketTestData(storeName));

        for(ProductTestData productTestData : products){
            BasketTestData currBasket = basketsAndStoreNames.get(productTestData.getStoreName());
            HashMap<ProductTestData,Integer> prodsAndAmounts = currBasket.getProductsAndAmountInBasket();
            prodsAndAmounts.put(productTestData,-1);
        }
        List<BasketTestData> baskets = new ArrayList<>(basketsAndStoreNames.values());

        return new CartTestData(baskets);
    }

    @Override
    public boolean deleteFromCurrentUserCart(ProductTestData productToDelete) {
        return serviceAPI.deleteFromCart(productToDelete.getProductName(),productToDelete.getStoreName());

    }

    @Override
    public boolean changeCurrentUserAmountOfProductInCart(ProductTestData productToChangeAmount, int newAmount) {

        return serviceAPI.editProductInCart(productToChangeAmount.getProductName(),productToChangeAmount.getStoreName(),newAmount);
    }

    @Override
    public boolean addToCurrentUserCart(ProductTestData productToAdd, int amount) {
        return serviceAPI.addProductToCart(productToAdd.getProductName(),productToAdd.getStoreName(),amount);
    }

    @Override
    public boolean writeReviewOnProduct(ProductTestData product, ReviewTestData review) {
        return serviceAPI.writeReview(product.getStoreName(),product.getProductName(),review.getContent());
    }

    @Override
    public List<ReviewTestData> getProductsReviews(ProductTestData product) {
        List<ProductData> products = serviceAPI.viewProductsInStore(product.getStoreName());
        for(ProductData pd : products)
            if(pd.getProductName().equals(product.getProductName()))
                return buildReviewsTestData(pd.getReviews());
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

        return approval? buildPurchaseTestData(serviceAPI.watchMyPurchaseHistory()) : null;
    }

    private  List<PurchaseTestData> buildPurchasesTestData(List<Purchase> purchaseHistory){
        List<PurchaseTestData> purchases = new ArrayList<>();
        for(Purchase purchase : purchaseHistory){
            PurchaseTestData toAddPurchase = buildPurchaseTestData(new ArrayList<>(Collections.singletonList(purchase)));
            toAddPurchase.setPurchaseDate(Date
                                        .from(purchase.getDate().atZone(ZoneId.systemDefault()).toInstant()));
            purchases.add(toAddPurchase);
        }
        return purchases;
    }

    private PurchaseTestData buildPurchaseTestData(List<Purchase> purchaseHistory){
        HashMap<ProductTestData,Integer> productsAndAmountInPurchase = new HashMap<>();
        Date date = new Date();

        for (Purchase purchase: purchaseHistory) {
            List<ProductData> products = purchase.getProduct();
            for (ProductData product: products) {
                productsAndAmountInPurchase.put(buildProductTestData(product)
                        ,product.getAmount());
            }
        }

        PurchaseTestData purchaseTestData = new PurchaseTestData(productsAndAmountInPurchase,
                                                                date,
                                                                0.0);
        double totalAmount = purchaseTestData.calculateTotalAmount();
        purchaseTestData.setTotalAmount(totalAmount);

        return purchaseTestData;
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
        return serviceAPI.addProductToStore(buildProductData(product));
    }

    private ProductData buildProductData(ProductTestData product) {
        String productName = product.getProductName();
        String storeName = product.getStoreName();
        return new ProductData(productName,
                                storeName,
                                product.getCategory(),
                                buildReviews(product.getReviews(),storeName,productName),
                                buildDiscounts(product.getDiscounts()),
                                product.getAmountInStore(),
                                product.getPrice(),
                                PurchaseTypeData.IMMEDDIATE);

    }

    private List<Discount> buildDiscounts(List<DiscountTestData> discountTestData){
        List<Discount> discounts = new ArrayList<>();
        for(DiscountTestData dst : discountTestData)
            discounts.add(new Discount(dst.getPercentage()));
        return discounts;
    }

    private List<Review> buildReviews(List<ReviewTestData> reviewTestData, String storeName, String productName){
        List<Review> apiReviews = new ArrayList<>();
        for(ReviewTestData rtd : reviewTestData)
            apiReviews.add(buildReview(rtd,storeName,productName));
        return apiReviews;
    }

    private Review buildReview(ReviewTestData review, String storeName, String productName) {
        return new Review(review.getWriter(), storeName,productName,review.getContent());
    }

    @Override
    public boolean deleteProduct(ProductTestData product) {
        return serviceAPI.removeProductFromStore(product.getStoreName(),product.getProductName());

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
        return serviceAPI.addManagerToStore(storeName,username);
    }

    @Override
    public boolean deleteManager(String storeName, String username) {
        return serviceAPI.removeManager(username,storeName);
    }

    @Override
    public List<PurchaseTestData> getStorePurchasesHistory(String storeName) {
        List<Purchase> purchases = serviceAPI.watchStoreHistory(storeName);
        return buildPurchasesTestData(purchases);
    }

    @Override
    public List<PurchaseTestData> getUserPurchaseHistory(String username) {
        List<Purchase> purchases = serviceAPI.AdminWatchUserPurchasesHistory(username);
        return buildPurchasesTestData(purchases);
    }

    @Override
    public List<PurchaseTestData> getCurrentUserPurchaseHistory() {
        List<Purchase> purchaseHistory = serviceAPI.watchMyPurchaseHistory();
        return buildPurchasesTestData(purchaseHistory);
    }

    @Override
    public boolean appointOwnerToStore(String storeName, String username) {
        return serviceAPI.manageOwner(storeName,username);
    }

    @Override
    public boolean addPermissionToManager(String storeName, String username, PermissionsTypeTestData permissionsTypeTestData) {
        PermissionType permissionType = buildPermissionType(permissionsTypeTestData);
        List<PermissionType> permissions = new ArrayList<>(Collections.singletonList(permissionType));
        return serviceAPI.addPermissions(permissions,storeName,username);
    }

    private PermissionType buildPermissionType(PermissionsTypeTestData permissionsTypeTestData) {
        switch (permissionsTypeTestData){
            case OWNER:
                return PermissionType.OWNER;
            case ADD_OWNER:
                return PermissionType.ADD_OWNER;
            case ADD_MANAGER:
                return PermissionType.ADD_MANAGER;
            case PRODUCTS_INVENTORY:
                return PermissionType.PRODUCTS_INVENTORY;
            default:
                return null;
        }
    }

    @Override
    public boolean deletePermission(String storeName, String username, PermissionsTypeTestData productsInventory) {
        return false;
    }

    //TODO: need to check, mainly request id
    @Override
    public boolean writeReplyToApplication(String storeName, ApplicationToStoreTestData application, String reply) {

        return serviceAPI.answerRequest(requestId, reply, application.getStoreName()) != null;
    }

    @Override
    public HashSet<ApplicationToStoreTestData> viewApplicationToStore(String storeName) {
        List<Request> requests = serviceAPI.watchRequestsOfStore(storeName);
        List<ApplicationToStoreTestData> applications = buildApplicationsToStore(requests);
        return new HashSet<>(applications);
    }

    @Override
    public HashMap<ApplicationToStoreTestData, String> getUserApplicationsAndReplies(String username, String storeName) {
        List<Request> requests = serviceAPI.watchRequestsOfStore(storeName);
        HashMap<ApplicationToStoreTestData,String> appsAndReplies = new HashMap<>();

        for(Request request : requests){
            if(request.getSenderName().equals(username)){
                String reply = request.getComment();
                ApplicationToStoreTestData application = buildApplicationToStore(request);
                if(reply!=null && !reply.equals(""))
                    appsAndReplies.put(application,reply);
            }
        }
        return appsAndReplies;
    }

    @Override
    public List<ApplicationToStoreTestData> getUserApplications(String username, String storeName) {
        HashMap<ApplicationToStoreTestData,String> appsAndReplies = getUserApplicationsAndReplies(username,storeName);
        List<ApplicationToStoreTestData> applications = new ArrayList<>();
        for(Map.Entry<ApplicationToStoreTestData,String> appAndRep : appsAndReplies.entrySet())
            applications.add(appAndRep.getKey());
        return applications;
    }

    private List<ApplicationToStoreTestData> buildApplicationsToStore(List<Request> requests) {
        List<ApplicationToStoreTestData> applications = new ArrayList<>();
        for(Request request : requests)
            applications.add(buildApplicationToStore(request));
        return applications;
    }

    private ApplicationToStoreTestData buildApplicationToStore(Request request) {
        return new ApplicationToStoreTestData(request.getStoreName(),request.getSenderName(),request.getContent());
    }

}
