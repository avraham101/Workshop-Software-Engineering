package EncoderDecoderConfig;

import DataAPI.PaymentData;
import DataAPI.PermissionType;
import DataAPI.ProductData;
import DataAPI.StoreData;
import Service.ServiceAPI;
import Service.SingleService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Decoder {

    private Gson gson;
    private Map<String,List<Map<String, Object>>> listMap;
    private HashMap<String, Integer> connected;
    private String idName;
    private ServiceAPI serviceAPI;

    public Decoder(String path) {
        gson = new Gson();
        listMap = new HashMap<>();
        connected = new HashMap<>();
        String json = readFile(path);
        Type type = new TypeToken<Map<String,List<Map<String, Object>>>>(){}.getType();
        listMap = gson.fromJson(json,type);
    }

    private String readFile(String path) {
        String output = "";
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                output += line;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    public void excecute() {
        List<Map<String,  Object>> functions = listMap.get("list");
        if(functions!=null) {
            for(Map<String, Object> func: functions) {
                Iterator<String> iterator = func.keySet().iterator();
                while (iterator.hasNext()) {
                    String nextKey = iterator.next();
                    executeFunc(nextKey, func);
                }
            }
            //executeLogout();
        }
    }

    private void executeFunc(String key, Map<String, Object> data) {
        try {
            switch (key) {
                case "idName":
                    this.idName = (String) data.get(key);
                    break;
                case "ServiceAPI":
                    executeServiceAPI(data);
                    break;
                case "register":
                    executeRegister(data);
                    break;
                case "login":
                    executeLogin(data);
                    break;
                case "deleteProductFromCart":
                    executeDeleteProductFromCart(data);
                    break;
                case "editProductInCart":
                    executeEditProductInCart(data);
                    break;
                case "addProductToCart":
                    executeAddProductToCart(data);
                    break;
                case "purchaseCart":
                    executePurchaseCart(data);
                    break;
                case "logout":
                    executeLogout(data);
                    break;
                case "openStore":
                    exceuteOpenStore(data);
                    break;
                case "writeReview":
                    executeWriteReview(data);
                    break;
                case "writeRequest":
                    executeWriteRequest(data);
                    break;
                case "addProductToStore":
                    executeAddProductToStore(data);
                    break;
                case "removeProductFromStore":
                    executeRemoveProduct(data);
                    break;
                case "editProductFromStore":
                    executeEditProductFromStore(data);
                    break;
                case "addDiscount":
                    executeAddDiscount(data);
                    break;
                case "deleteDiscountFromStore":
                    executeRemoveDiscountFromStore(data);
                    break;
                case "updatePolicy":
                    executeUpdatePolicy(data);
                    break;
                case "manageOwner":
                    executeManageOwner(data);
                    break;
                case "approveManageOwner":
                    executeApproveManageOwner(data);
                    break;
                case "addManagerToStore":
                    executeAddManagerToStore(data);
                    break;
                case "addPermissions":
                    executeAddPermissions(data);
                    break;
                case "removePermissions":
                    executeRemovePermissions(data);
                    break;
                case "removeManager":
                    executeRemoveManager(data);
                    break;
                case "answerRequest":
                    executeAnswerRequest(data);
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    /**
     * use case 1.1 - Init Trading System
     * @param data
     */
    private void executeServiceAPI(Map<String, Object> data) {
        Map<String, Object> value = (Map<String, Object>)data.get("ServiceAPI");
        String userName = (String) value.get("userName");
        String password = (String) value.get("password");
        serviceAPI = SingleService.getInstance(userName, password);
    }

    /**
     * use case 2.2 - Register
     * @param data
     */
    private void executeRegister(Map<String, Object> data) {
        Map<String, Object> value = (Map<String, Object>)data.get("register");
        String userName = (String) value.get("userName");
        String password = (String) value.get("password");
        serviceAPI.register(userName,password);
//        int id = serviceAPI.connectToSystem();
//        connected.put(userName, id);
//        serviceAPI.login(id,userName,password);
    }

    /**
     * use case 2.3 - Login
     * @param data
     */
    private void executeLogin(Map<String, Object> data) {
        Map<String, Object> value = (Map<String, Object>)data.get("login");
        String userName = (String) value.get("username");
        String password = (String) value.get("password");
        int id = serviceAPI.connectToSystem();
        if(serviceAPI.login(id,userName,password).getValue())
            connected.put(userName,id);
    }

    /**
     * use case 2.7.2 - delete product from cart
     */
    private void executeDeleteProductFromCart(Map<String, Object> data){
        String name = (String) data.get("idName");
        int id = connected.get(name);
        Map<String, Object> value = (Map<String, Object>)data.get("deleteProductFromCart");
        String storeName = (String) value.get("storeName");
        String productName = (String) value.get("productName");
        serviceAPI.deleteFromCart(id,productName,storeName);
    }

    /**
     * use case 2.7.3 - edit product in cart
     * @param data
     */
    private void executeEditProductInCart(Map<String, Object> data){
        String name = (String) data.get("idName");
        int id = connected.get(name);
        Map<String, Object> value = (Map<String, Object>)data.get("editProductInCart");
        String storeName = (String) value.get("storeName");
        String productName = (String) value.get("productName");
        int newAmount = ((Double)value.get("newAmount")).intValue();
        serviceAPI.editProductInCart(id,productName,storeName,newAmount);
    }

    /**
     * use case 2.7.4 - add product to cart
     * @param data
     */
    private void executeAddProductToCart(Map<String,Object> data){
        String name = (String) data.get("idName");
        int id = connected.get(name);
        Map<String, Object> value = (Map<String, Object>)data.get("addProductToCart");
        String storeName = (String) value.get("storeName");
        String productName = (String) value.get("productName");
        int amount = ((Double)value.get("amount")).intValue();
        serviceAPI.addProductToCart(id,productName,storeName,amount);
    }

    /**
     * use case 2.8 - purchaseCart
     * @param data
     */
    private void executePurchaseCart(Map<String,Object> data){
        String name = (String) data.get("idName");
        int id = connected.get(name);
        Map<String, Object> value = (Map<String, Object>)data.get("purchaseCart");
        String country = (String) value.get("country");
        String addressToDeliver = (String) value.get("addressToDeliver");
        String city = (String) value.get("city");
        int zip = (int) value.get("zip");
        PaymentData paymentData = gson.fromJson(gson.toJson(value.get("paymentData")),
                PaymentData.class);
        serviceAPI.purchaseCart(id,country,paymentData,addressToDeliver,city,zip);
    }


    /**
     * use case 3.1 - Logout
     */
    private void executeLogout(Map<String, Object> data) {
        //Map<String, Object> value = (Map<String, Object>)data.get("logout");
        String name = (String) data.get("idName");
        int id = connected.get(name);
        if(serviceAPI.logout(id).getValue())
            connected.remove(name);
    }

    /**
     * use case 3.2 - Open Store
     * @param data
     */
    private void exceuteOpenStore(Map<String, Object> data) {
        String name = (String) data.get("idName");
        int id = connected.get(name);
        Map<String, Object> value = (Map<String, Object>)data.get("openStore");
        StoreData storeDetails = gson.fromJson(gson.toJson(value.get("storeDetails")),
                StoreData.class);
        serviceAPI.openStore(id,storeDetails);
    }

    /**
     * use case 3.3 - WriteReview
     * @param data
     */
    private void executeWriteReview(Map<String, Object> data){
        String name = (String) data.get("idName");
        int id = connected.get(name);
        Map<String, Object> value = (Map<String, Object>)data.get("writeReview");
        String storeName = (String) value.get("storeName");
        String productName = (String) value.get("productName");
        String content = (String) value.get("content");
        serviceAPI.writeReview(id,storeName,productName,content);
    }

    /**
     * use case 3.5 - writeRequestToStore
     * @param data
     */
    private void executeWriteRequest(Map<String, Object> data){
        String name = (String) data.get("idName");
        int id = connected.get(name);
        Map<String, Object> value = (Map<String, Object>)data.get("writeRequest");
        String storeName = (String) value.get("storeName");
        String content = (String) value.get("content");
        serviceAPI.writeRequestToStore(id,storeName,content);
    }

    /**
     * use case 4.1.1 - addProductToStore
     * @param data
     */
    private void executeAddProductToStore(Map<String, Object> data) {
        String name = (String) data.get("idName");
        int id = connected.get(name);
        Map<String, Object> value = (Map<String, Object>)data.get("addProductToStore");
        ProductData productData = gson.fromJson(gson.toJson(value.get("productData")),
                ProductData.class);
        serviceAPI.addProductToStore(id,productData);
    }

    /**
     * use case 4.1.2 - removeProductFromStore
     * @param data
     */
    private void executeRemoveProduct(Map<String, Object> data){
        String name = (String) data.get("idName");
        int id = connected.get(name);
        Map<String, Object> value = (Map<String, Object>)data.get("removeProductFromStore");
        String storeName = (String) value.get("storeName");
        String productName = (String) value.get("productName");
        serviceAPI.removeProductFromStore(id,storeName,productName);
    }

    /**
     * use case 4.1.3 - editProductFromStore
     * @param data
     */
    private void executeEditProductFromStore(Map<String, Object> data){
        String name = (String) data.get("idName");
        int id = connected.get(name);
        Map<String, Object> value = (Map<String, Object>)data.get("editProductFromStore");
        ProductData productData = gson.fromJson(gson.toJson(value.get("productData")),
                ProductData.class);
        serviceAPI.editProductFromStore(id,productData);
    }

    /**
     * use case 4.2.1.1 - addDiscount
     * @param data
     */
    private void executeAddDiscount(Map<String, Object>data){
        String name = (String) data.get("idName");
        int id = connected.get(name);
        Map<String, Object> value = (Map<String, Object>)data.get("addDiscount");
        String discountData = (String) value.get("discountData");
        String storeName = (String) value.get("storeName");
        serviceAPI.addDiscount(id,discountData,storeName);
    }

    /**
     * use case 4.2.1.2 - deleteDiscountFromStore
     * @param data
     */
    private void executeRemoveDiscountFromStore(Map<String, Object> data){
        String name = (String) data.get("idName");
        int id = connected.get(name);
        Map<String, Object> value = (Map<String, Object>)data.get("deleteDiscountFromStore");
        int discountId= ((Double) value.get("discountId")).intValue();
        String storeName = (String) value.get("storeName");
        serviceAPI.deleteDiscountFromStore(id,discountId,storeName);
    }

    /**
     * use case - 4.2.2.1 - updatePolicy
     * @param data
     */
    private void executeUpdatePolicy(Map<String, Object> data){
        String name = (String) data.get("idName");
        int id = connected.get(name);
        Map<String, Object> value = (Map<String, Object>)data.get("updatePolicy");
        String storeName = (String) value.get("storeName");
        String policyData = (String) value.get("policyData");
        serviceAPI.upadtePolicy(id,policyData,storeName);
    }

    /**
     * use case 4.3.1 - ManageOwner
     * @param data
     */
    private void executeManageOwner(Map<String, Object> data){
        String name = (String) data.get("idName");
        int id = connected.get(name);
        Map<String, Object> value = (Map<String, Object>)data.get("manageOwner");
        String storeName = (String) value.get("storeName");
        String userName = (String) value.get("userName");
        serviceAPI.manageOwner(id,storeName,userName);
    }

    /**
     * use case 4.3.2 - ApproveManageOwner
     * @param data
     */
    private void executeApproveManageOwner(Map<String, Object> data){
        String name = (String) data.get("idName");
        int id = connected.get(name);
        Map<String, Object> value = (Map<String, Object>)data.get("approveManageOwner");
        String storeName = (String) value.get("storeName");
        String userName = (String) value.get("userName");
        serviceAPI.approveManageOwner(id,storeName,userName);
    }

    /**
     * use case 4.5 - add manager to store
     * @param data
     */
    private void executeAddManagerToStore(Map<String, Object> data) {
        String name = (String) data.get("idName");
        int id = connected.get(name);
        Map<String, Object> value = (Map<String, Object>)data.get("addManagerToStore");
        String storeName = (String) value.get("storeName");
        String userName = (String) value.get("userName");
        serviceAPI.addManagerToStore(id,storeName,userName);
    }

    /**
     * use case 4.6.1 - add permissions
     * @param data
     */
    private void executeAddPermissions(Map<String, Object> data) {
        String name = (String) data.get("idName");
        int id = connected.get(name);
        Map<String, Object> value = (Map<String, Object>)data.get("addPermissions");
        Type type = new TypeToken<List<PermissionType>>(){}.getType();
        List<PermissionType> permissions = gson.fromJson(gson.toJson(value.get("permissions")),type);
        String storeName = (String) value.get("storeName");
        String userName = (String) value.get("userName");
        serviceAPI.addPermissions(id, permissions, storeName, userName);
    }
    /**
     * use case 4.6.2 - remove permissions
     * @param data
     */
    private void executeRemovePermissions(Map<String, Object> data){
        String name = (String) data.get("idName");
        int id = connected.get(name);
        Map<String, Object> value = (Map<String, Object>)data.get("removePermissions");
        Type type = new TypeToken<List<PermissionType>>(){}.getType();
        List<PermissionType> permissions = gson.fromJson(gson.toJson(value.get("permissions")),type);
        String storeName = (String) value.get("storeName");
        String userName = (String) value.get("userName");
        serviceAPI.removePermissions(id, permissions, storeName, userName);
    }

    /**
     * use case 4.7 - removeManager
     * @param data
     */
    private void executeRemoveManager(Map<String, Object> data){
        String name = (String) data.get("idName");
        int id = connected.get(name);
        Map<String, Object> value = (Map<String, Object>)data.get("removeManager");
        String storeName = (String) value.get("storeName");
        String userName = (String) value.get("userName");
        serviceAPI.removeManager(id,userName,storeName);
    }

    /**
     * use case 4.9.2 - answerRequest
     * @param data
     */
    private void executeAnswerRequest(Map<String, Object> data){
        String name = (String) data.get("idName");
        int id = connected.get(name);
        Map<String, Object> value = (Map<String, Object>)data.get("answerRequest");
        String storeName = (String) value.get("storeName");
        String content = (String) value.get("content");
        int requestId = ((Double) value.get("requestId")).intValue();
        serviceAPI.answerRequest(id,requestId,content,storeName);
    }
}
