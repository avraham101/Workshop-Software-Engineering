package AcceptanceTests.AcceptanceTestsBridge;

import AcceptanceTests.AcceptanceTestDataObjects.*;
import AcceptanceTests.AcceptanceTestDataObjects.FilterTestData.CategoryFilterTestData;
import AcceptanceTests.AcceptanceTestDataObjects.FilterTestData.FilterTestData;
import AcceptanceTests.AcceptanceTestDataObjects.FilterTestData.PriceRangeFilterTestData;
import AcceptanceTests.AcceptanceTestDataObjects.FilterTestData.ProductNameFilterTestData;
import DataAPI.*;
import Domain.*;
import Service.ServiceAPI;
import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;

import java.util.*;

public class AcceptanceTestsRealBridge implements AcceptanceTestsBridge {
    private ServiceAPI serviceAPI;
    private int id=0;

    //---------------------------Use-Case-1.1---------------------------------//
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
    public boolean initialStart(String username , String password
            , PaymentSystem paymentSystem, SupplySystem deliverySystem){
        try{
            this.serviceAPI = new ServiceAPI(username,password,paymentSystem,deliverySystem);
            return true;
        }catch (Exception e){
            return false;
        }

    }
    //---------------------------Use-Case-1.1---------------------------------//


    //---------------------------Use-Case-2.2---------------------------------//
    @Override
    public boolean register(String username, String password) {
        return serviceAPI.register(username,password);
    }
    //---------------------------Use-Case-2.2---------------------------------//


    //---------------------------Use-Case-2.3---------------------------------//
    @Override
    public boolean login(int id, String username, String password) {
        return serviceAPI.login(id,username,password);

    }
    //---------------------------Use-Case-2.3---------------------------------//

    //---------------------------Use-Case-2.4---------------------------------//
    @Override
    public List<StoreTestData> getStoresInfo() {
        List<StoreData> storeData = serviceAPI.viewStores();
        return buildStoresTestData(storeData);
    }

    @Override
    public List<ProductTestData> getStoreProducts(String storeName) {
        return buildProductsTestData(serviceAPI.viewProductsInStore(storeName));
    }
    //---------------------------Use-Case-2.4---------------------------------//

    //---------------------------Use-Case-2.5---------------------------------//
    @Override
    public HashSet<ProductTestData> filterProducts(List<FilterTestData> filters) {
        Filter productsFilter = buildFilterFromTestFilters(filters);
        List<ProductData> filteredProducts =  serviceAPI.viewSpasificProducts(productsFilter);
        return new HashSet<>(buildProductsTestData(filteredProducts));
    }
    //---------------------------Use-Case-2.5---------------------------------//

    //---------------------------Use-Case-2.7---------------------------------//
    @Override
    public CartTestData getUsersCart(int id) {
        CartData cartData = serviceAPI.watchCartDetatils(id);
        return buildCartTestData(cartData);
    }

    @Override
    public boolean deleteFromUserCart(int id, ProductTestData productToDelete) {
        return serviceAPI.deleteFromCart(id,productToDelete.getProductName(),productToDelete.getStoreName());
    }

    @Override
    public boolean changeUserAmountOfProductInCart(int id, ProductTestData productToChangeAmount, int newAmount) {
        return serviceAPI.editProductInCart(id,productToChangeAmount.getProductName(),productToChangeAmount.getStoreName(),newAmount);
    }

    @Override
    public boolean addToUserCart(int id, ProductTestData productToAdd, int amount) {
        return serviceAPI.addProductToCart(id,productToAdd.getProductName(),productToAdd.getStoreName(),amount);
    }
    //---------------------------Use-Case-2.7---------------------------------//

    //---------------------------Use-Case-2.8---------------------------------//
    @Override
    public boolean buyCart(PaymentTestData paymentMethod, DeliveryDetailsTestData deliveryDetails) {
        PaymentData paymentData;
        String delivery;
        if(deliveryDetails!=null){
            delivery= deliveryDetails.toString();
        }
        else{
            delivery=null;
        }
        if(paymentMethod!=null){
            paymentData = new PaymentData(paymentMethod.getCreditCardOwner(),"addr",paymentMethod.getCreditCardNumber());
        }
        else{
            paymentData=null;
        }

        boolean approval = serviceAPI.purchaseCart(id,paymentData,delivery);
        return approval;
    }
    //---------------------------Use-Case-2.8---------------------------------//

    //---------------------------Use-Case-3.1---------------------------------//
    @Override
    public boolean logout(int id) {
        return serviceAPI.logout(id);
    }
    //---------------------------Use-Case-3.1---------------------------------//

    //---------------------------Use-Case-3.2---------------------------------//
    @Override
    public StoreTestData openStore(int id,String storeName) {
        StoreData store = new StoreData(storeName,new PurchasePolicy(),new DiscountPolicy());
        boolean approval = serviceAPI.openStore(id,store);
        if(!approval) {
            return null;
        }
        else{
            return getStoreInfoByName(storeName);
        }
    }
    //---------------------------Use-Case-3.2---------------------------------//

    //---------------------------Use-Case-3.3---------------------------------//
    @Override
    public boolean writeReviewOnProduct(int id,ProductTestData product, ReviewTestData review) {
        return serviceAPI.writeReview(id,product.getStoreName(),product.getProductName(),review.getContent());
    }
    //---------------------------Use-Case-3.3---------------------------------//

    //---------------------------Use-Case-3.5---------------------------------//
    @Override
    public boolean sendApplicationToStore(int id, String storeName, String message) {
        return serviceAPI.writeRequestToStore(id,storeName,message);
    }
    //---------------------------Use-Case-3.5---------------------------------//

    //---------------------------Use-Case-3.7---------------------------------//
    @Override
    public List<PurchaseTestData> getCurrentUserPurchaseHistory() {
        List<Purchase> purchaseHistory = serviceAPI.watchMyPurchaseHistory(id);
        return buildPurchasesTestData(purchaseHistory);
    }
    //---------------------------Use-Case-3.7---------------------------------//

    //---------------------------Use-Case-4.1---------------------------------//
    @Override
    public boolean addProduct(int id,ProductTestData product) {
        return serviceAPI.addProductToStore(id,buildProductData(product));
    }

    @Override
    public boolean deleteProduct(int id,ProductTestData product) {
        return serviceAPI.removeProductFromStore(id,product.getStoreName(),product.getProductName());
    }

    @Override
    public boolean editProductInStore(int id,ProductTestData product) {
        ProductData productData = buildProductData(product);
        return serviceAPI.editProductFromStore(id,productData);
    }
    //---------------------------Use-Case-4.1---------------------------------//

    //---------------------------Use-Case-4.3---------------------------------//
    @Override
    public boolean appointOwnerToStore(int id,String storeName, String username) {
        return serviceAPI.manageOwner(id,storeName,username);
    }
    //---------------------------Use-Case-4.3---------------------------------//

    //---------------------------Use-Case-4.5---------------------------------//
    @Override
    public boolean appointManager(int id,String storeName, String username) {
        return serviceAPI.addManagerToStore(id,storeName,username);
    }
    //---------------------------Use-Case-4.5---------------------------------//

    //---------------------------Use-Case-4.6---------------------------------//
    @Override
    public boolean addPermissionToManager(int id,String storeName, String username, PermissionsTypeTestData permissionsTypeTestData) {
        PermissionType permissionType = buildPermissionType(permissionsTypeTestData);
        List<PermissionType> permissions = new ArrayList<>(Collections.singletonList(permissionType));
        return serviceAPI.addPermissions(id,permissions,storeName,username);
    }

    @Override
    public boolean deletePermission(int id,String storeName, String username, PermissionsTypeTestData permissionsTypeTestData) {
        List<PermissionType> permissionType = new ArrayList<>(Arrays.asList(buildPermissionType(permissionsTypeTestData)));
        return serviceAPI.removePermissions(id,permissionType,storeName,username);
    }
    //---------------------------Use-Case-4.6---------------------------------//

    //---------------------------Use-Case-4.7---------------------------------//
    @Override
    public boolean deleteManager(int id, String storeName, String username) {
        return serviceAPI.removeManager(id,username,storeName);
    }
    //---------------------------Use-Case-4.7---------------------------------//

    //---------------------------Use-Case-4.9---------------------------------//
    @Override
    public HashSet<ApplicationToStoreTestData> viewApplicationToStore(int id, String storeName) {
        List<Request> requests = serviceAPI.watchRequestsOfStore(id,storeName);
        List<ApplicationToStoreTestData> applications = buildApplicationsToStore(requests);
        return new HashSet<>(applications);
    }

    @Override
    public HashMap<ApplicationToStoreTestData, String> getUserApplicationsAndReplies(int id, String username, String storeName) {
        List<Request> requests = serviceAPI.watchRequestsOfStore(id,storeName);
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
    public List<ApplicationToStoreTestData> getUserApplications(int id,String username, String storeName) {
        List<Request> requests = serviceAPI.watchRequestsOfStore(id,storeName);
        return buildApplicationsToStore(requests);
    }


    //---------------------------Use-Case-4.9---------------------------------//

    //---------------------------Use-Case-4.10---------------------------------//
    @Override
    public List<PurchaseTestData> userGetStorePurchasesHistory(int id ,String storeName) {
        return buildPurchasesTestData(serviceAPI.watchStoreHistory(id,storeName));
    }


    //---------------------------Use-Case-4.10---------------------------------//

    //---------------------------Use-Case-6.4---------------------------------//
    @Override
    public List<PurchaseTestData> getUserPurchaseHistory(String username) {
        List<Purchase> purchases = serviceAPI.AdminWatchUserPurchasesHistory(id,username);
        return buildPurchasesTestData(purchases);
    }

    @Override
    public List<PurchaseTestData> getStorePurchasesHistory(String storeName) {
        List<Purchase> purchases = serviceAPI.AdminWatchStoreHistory(id,storeName);
        return buildPurchasesTestData(purchases);
    }
    //---------------------------Use-Case-6.4---------------------------------//


    //----------------------RealBridge aux functions--------------------------//
    @Override
    public int connect() {
        return serviceAPI.connectToSystem();
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
    public void addProducts(int id,List<ProductTestData> products) {
        for(ProductTestData productTestData : products){
            ProductData productData = buildProductData(productTestData);
            boolean test=serviceAPI.addProductToStore(id,productData);
            int t =3;
        }
    }

    private List<StoreTestData> buildStoresTestData(List<StoreData> storeData){
        List<StoreTestData> storeTestData = new ArrayList<>();
        for(StoreData sd : storeData)
            storeTestData.add(buildStoreTestData(sd));
        return storeTestData;
    }

    private StoreTestData buildStoreTestData(StoreData storeData) {
        try {
            String storeName = storeData.getName();
            List<ProductData> storeProducts = serviceAPI.viewProductsInStore(storeName);
            List<ProductTestData> storeTestProducts = buildProductsTestData(storeProducts);
            StoreTestData storeToReturn = new StoreTestData(storeName, null);
            storeToReturn.setProducts(storeTestProducts);
            return storeToReturn;
        }catch (NullPointerException e){
            return null;
        }
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

    private Filter buildFilterFromTestFilters(List<FilterTestData> filters) {
        Search search = Search.NONE;
        double minValue = 0, maxValue = Integer.MAX_VALUE;
        String category = "", value = "";

        for(FilterTestData filterTestData : filters){
            switch (filterTestData.getFilterType()){
                case CATEGORY:
                    search = Search.CATEGORY;
                    category = ((CategoryFilterTestData) filterTestData).getCategory();
                    value = category;
                    break;
                case PRICE_RANGE:
                    minValue = ((PriceRangeFilterTestData) filterTestData).getLowestPrice();
                    maxValue = ((PriceRangeFilterTestData) filterTestData).getHighestPrice();
                    break;
                case PRODUCT_NAME:
                    search = Search.PRODUCT_NAME;
                    value = ((ProductNameFilterTestData) filterTestData).getProductName();
                    break;
                default:
                    break;
            }
        }

        return new Filter(search,value,minValue,maxValue,category);
    }

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
            prodsAndAmounts.put(productTestData,productTestData.getAmountInStore());
        }
        List<BasketTestData> baskets = new ArrayList<>(basketsAndStoreNames.values());

        return new CartTestData(baskets);
    }

    //TODO: fix
    @Override
    public StoreTestData getStoreInfoByName(String storeName) {

        List<StoreData> stores = serviceAPI.viewStores();
        for (StoreData st: stores) {
            if(st.getName().equals(storeName)){
                return new StoreTestData(storeName,null);
            }
        }
        return null;
    }

    private List<PurchaseTestData> buildPurchasesTestData(List<Purchase> history) {
        if(history!=null) {
            List<PurchaseTestData> purchasesTestData = new ArrayList<>();
            double totalCost = 0;
            Date date = new Date();
            for (Purchase purchase : history) {
                HashMap<ProductTestData, Integer> productsAndAmountInPurchase = new HashMap<>();
                List<ProductData> products = purchase.getProduct();
                for (ProductData product : products) {
                    productsAndAmountInPurchase.put(buildProductTestData(product)
                            , product.getAmount());
                    totalCost += product.getPriceAfterDiscount() * product.getAmount();

                }
                PurchaseTestData purchaseTestData = new PurchaseTestData
                        (productsAndAmountInPurchase, date, totalCost);
                purchasesTestData.add(purchaseTestData);
                totalCost = 0;
            }

            return purchasesTestData;
        }
        return null;
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
    public List<ReviewTestData> getProductsReviews(ProductTestData product) {
        List<ProductData> products = serviceAPI.viewProductsInStore(product.getStoreName());
        for(ProductData pd : products)
            if(pd.getProductName().equals(product.getProductName()))
                return buildReviewsTestData(pd.getReviews());
        return null;
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
    public void changeAmountOfProductInStore(int id,ProductTestData product, int amount) {
        product.setAmountInStore(amount);
        editProductInStore(id,product);
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
    public boolean writeReplyToApplication(int id,int requestId,String storeName, ApplicationToStoreTestData application, String reply) {
        return serviceAPI.answerRequest(id,requestId, reply, storeName) != null;
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

    //----------------------RealBridge aux functions--------------------------//

//    public PurchaseTestData getPurchaseTestDataFromHistory(){
//        List<Purchase>  history =  serviceAPI.watchMyPurchaseHistory();
//        if(history==null)
//            return null;
//        HashMap<ProductTestData,Integer> productsAndAmountInPurchase = new HashMap<>();
//        double totalCost=0;
//        Date date = new Date();
//        for (Purchase purchase: history) {
//            List<ProductData> products = purchase.getProduct();
//            for (ProductData product: products) {
//                productsAndAmountInPurchase.put(buildProductTestData(product)
//                        ,product.getAmount());
//                totalCost+=product.getPriceAfterDiscount()*product.getAmount();
//
//            }
//        }
//        PurchaseTestData purchaseTestData = new PurchaseTestData
//                (productsAndAmountInPurchase, date,totalCost);
//        return  purchaseTestData;
//    }


//    public PurchaseTestData getPurchaseTestDataFromHistory(){
//        List<Purchase>  history =  serviceAPI.watchMyPurchaseHistory();
//        HashMap<ProductTestData,Integer> productsAndAmountInPurchase = new HashMap<>();
//        double totalCost=0;
//        Date date = new Date();
//        for (Purchase purchase: history) {
//            List<ProductData> products = purchase.getProduct();
//            for (ProductData product: products) {
//                productsAndAmountInPurchase.put(buildProductTestData(product)
//                        ,product.getAmount());
//                totalCost+=product.getPriceAfterDiscount()*product.getAmount();
//
//        for (Purchase purchase: purchaseHistory) {
//            List<ProductData> products = purchase.getProduct();
//            for (ProductData product: products) {
//                productsAndAmountInPurchase.put(buildProductTestData(product)
//                        ,product.getAmount());
//            }
//        }
//
//        PurchaseTestData purchaseTestData = new PurchaseTestData(productsAndAmountInPurchase,
//                                                                date,
//                                                                0.0);
//        double totalAmount = purchaseTestData.calculateTotalAmount();
//        purchaseTestData.setTotalAmount(totalAmount);
//
//        return purchaseTestData;
//
//
//        }
//        PurchaseTestData purchaseTestData = new PurchaseTestData
//                (productsAndAmountInPurchase, date,totalCost);
//        return  purchaseTestData;
//    }
}
