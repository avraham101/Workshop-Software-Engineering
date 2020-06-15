package Data;

import DataAPI.ProductData;
import DataAPI.PurchaseTypeData;
import DataAPI.RequestData;
import DataAPI.StoreData;
import Domain.Request;
import Domain.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class TestDataThreads extends TestData {

    private int numThreads;
    private int numStores;
    private int numProductsForStore;
    private int numRequestPerStore;

    private Map<String,Integer> ids;
    private List<Subscribe> users;
    private List<StoreData> stores;
    private Map<String, List<ProductData>> productsPerStore;
    private List<RequestData> requests;

    public TestDataThreads(int numThreads){
        this.numThreads = numThreads;
        initLists();
    }

    public TestDataThreads(int numThreads, int numStores){
        this.numThreads = numThreads;
        this.numStores = numStores;
        initLists();
    }

    public TestDataThreads(int numThreads, int numStores, int numProductsForStore){
        this.numThreads = numThreads;
        this.numStores = numStores;
        this.numProductsForStore = numProductsForStore;
        this.numRequestPerStore = 2;
        initLists();
        setUpIds();
        setUpUsers();
        setUpStores();
        setUpProducts();
        setUpRequests();
    }

    private void initLists(){
        this.ids = new ConcurrentHashMap<>();
        this.users = new CopyOnWriteArrayList<>();
        this.stores = new CopyOnWriteArrayList<>();
        this.productsPerStore = new ConcurrentHashMap<String, List<ProductData>>();
        this.requests = new CopyOnWriteArrayList<>();
    }

    private void setUpIds(){
        ids.put("admin",0);
        for(int i=1;i<= numThreads;i++)
            ids.put("t"+i,i);
    }

    private void setUpUsers(){
        users.add(new Subscribe("admin","admin"));
        for(int i=1;i<=numThreads;i++){
            String namePass = "t"+i;
            users.add(new Subscribe(namePass,namePass));
        }
    }

    private void setUpStores(){
        for(int i=1;i<=numStores;i++){
            String name = "s"+i;
            String desc = "d"+i;
            stores.add(new StoreData(name,desc));
        }
    }

    private void setUpProducts(){
        for(int i=0;i<stores.size();i++) {
            String storeName = stores.get(i).getName();
            productsPerStore.put(storeName,new ArrayList<ProductData>());
            for (int j = 1; j <= numProductsForStore; j++) {
                String productName = "p"+i+""+j;
                productsPerStore.get(storeName).add(new ProductData(productName,storeName,"c"+i,null,10,10, PurchaseTypeData.IMMEDDIATE));
            }
        }
    }

    private void setUpRequests(){
        for(Subscribe user : users) {
            for (int i = 0; i < stores.size(); i++) {
                String storeName = stores.get(i).getName();
                for (int j = 1; j <= numRequestPerStore; j++) {
                    String content = "r" + i + "" + j;
                    Request request = new Request(user.getName(),storeName,content,-1);
                    request.setId(-1);
                    requests.add(new RequestData(request));
                }
            }
        }

    }

    public Map<String, Integer> getIds() {
        return ids;
    }

    public List<Subscribe> getUsers() {
        return users;
    }

    public List<StoreData> getStores() {
        return stores;
    }

    public Map<String, List<ProductData>> getProductsPerStore() {
        return productsPerStore;
    }

    public List<RequestData> getRequests() {
        return requests;
    }

    public void setNumRequestPerStore(int numRequestPerStore) {
        this.numRequestPerStore = numRequestPerStore;
    }
}
