package LogicManagerTests;

import Data.TestDataThreads;
import DataAPI.*;
import Domain.*;
import Persitent.Cache;
import Persitent.DaoHolders.DaoHolder;
import Publisher.SinglePublisher;
import Stubs.StubPublisher;
import Systems.PaymentSystem.PaymentSystem;
import Systems.PaymentSystem.ProxyPayment;
import Systems.SupplySystem.ProxySupply;
import Systems.SupplySystem.SupplySystem;
import org.junit.*;

import javax.transaction.Transactional;

import java.util.*;
import java.util.concurrent.*;

import static Utils.Utils.TestMode;
import static org.junit.Assert.*;
import static org.junit.Assert.fail;

/**
 * checks that functionally on the system is capable of handling multiple users
 */
public class LogicManagerThreadsTests {
    private final int NUM_THREADS = 5;
    private final int NUM_STORES = 5;
    private final int NUM_PRODUCTS = 6;
    private final int TIMEOUT = 50;
    private final TimeUnit TIME_UNIT = TimeUnit.MINUTES;

    private TestDataThreads threadsData;
    private ExecutorService threadPool;
    private List<Subscribe> users;
    private List<Subscribe> newUsers;
    private List<StoreData> stores;
    private Map<String, List<ProductData>> productsPerStore;
    private List<RequestData> requests;
    private Subscribe admin;
    private Map<String, Integer> ids;
    private static DaoHolder daos;
    private SupplySystem supplySystem;
    private PaymentSystem paymentSystem;
    private Cache cache;
    private StubPublisher publisher;
    private LogicManager logicManager;


    /**
     * use case 2.2 - Register
     * checks exactly one thread registered successfully
     */
    @Test
    public void testRegisterSuccessOnce(){

        Subscribe subscribe = newUsers.get(0);
        Callable<Response<?>> callable = ()-> logicManager.register(subscribe.getName(),subscribe.getPassword());

        List<Future<Response<?>>> futures = new CopyOnWriteArrayList<>();
        List<Response<?>> results = new CopyOnWriteArrayList<>();

        for(int i=0;i<NUM_THREADS;i++)
            futures.add(submitTask(callable));

        for(Future<Response<?>> future : futures) {
            try {
                results.add(future.get(TIMEOUT, TIME_UNIT));
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
                fail();
            }
        }

        assertTrue(checkOnlyOneSuccess(results));

        Subscribe actual = cache.findSubscribe(subscribe.getName());
        assertNotNull(actual);

        tearDownRegister();
    }

    /**
     * use case 2.2 - Register
     * tests all threads registered successfully
     */
    @Test
    public void testRegisterSuccess() {
        List<Response<?>> results = new CopyOnWriteArrayList<>();
        List<Future<Response<?>>> futures = new CopyOnWriteArrayList<>();

        for(Subscribe user : newUsers) {
            Callable<Response<?>> callable = () -> logicManager.register(user.getName(), user.getPassword());
            futures.add(submitTask(callable));
        }

        for(Future<Response<?>> future : futures){
            try {
                results.add(future.get(TIMEOUT,TIME_UNIT));
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                fail();
            }
        }
        assertTrue(checkAllSuccess(results));

        for(Subscribe user : newUsers)
            assertNotNull(cache.findSubscribe(user.getName()));

        tearDownRegister();
    }

    /**
     * use case 2.3 - Login
     * checks exactly one thread is Logged In successfully
     */
    @Test
    public void testLoginSuccessOnce(){
        Subscribe newUser = newUsers.get(0);
        registerUsers(newUsers);
        setUpConnect();

        List<Response<?>> results = new CopyOnWriteArrayList<>();
        List<Future<Response<?>>> futures = new CopyOnWriteArrayList<>();
        for(Subscribe user : newUsers){
            Callable<Response<?>> callable = ()->
                    logicManager.login(ids.get(user.getName()),newUser.getName(),newUser.getPassword());
                futures.add(submitTask(callable));
        }

        for(Future<Response<?>> future : futures){
            try {
                results.add(future.get(TIMEOUT,TIME_UNIT));
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                fail();
            }
        }


        for(Subscribe user : newUsers){
            int sessionNumber = cache.findSubscribe(user.getName()).getSessionNumber();
            if (user.getName().equals(newUser.getName()))
                assertNotEquals(sessionNumber, -1);
            else
                assertEquals(sessionNumber, -1);
        }

        assertTrue(checkOnlyOneSuccess(results));

        tearDownRegister();
        tearDownConnect();
    }

    /**
     * use case 2.3 - Login
     * checks all threads Logged In successfully
     */
    @Test
    public void testLoginSuccess(){
        registerUsers(newUsers);
        setUpConnect();

        List<Response<?>> results = new CopyOnWriteArrayList<>();
        List<Future<Response<?>>> futures = new CopyOnWriteArrayList<>();

        for(Subscribe user : newUsers){
            Callable<Response<?>> callable = ()->
                    logicManager.login(ids.get(user.getName()),user.getName(),user.getPassword());
            futures.add(submitTask(callable));
        }

        for(Future<Response<?>> future : futures){
            try {
                results.add(future.get(TIMEOUT,TIME_UNIT));
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                fail();
            }
        }

        assertTrue(checkAllSuccess(results));

        for(Subscribe user : newUsers){
            int sessionNumber = cache.findSubscribe(user.getName()).getSessionNumber();
            assertNotEquals(sessionNumber,-1);
        }
        tearDownRegister();
        tearDownConnect();
    }

    /**
     * use case 3.1 - Logout
     * checks that all logged in users logged out successfully
     */
    @Test
    public void testLogoutSuccess(){
        registerAndLoginUsers(users);

        List<Response<?>> results = new CopyOnWriteArrayList<>();
        List<Future<Response<?>>> futures = new CopyOnWriteArrayList<>();

        for(Subscribe user : users){
            Callable<Response<?>> callable = ()-> logicManager.logout(ids.get(user.getName()));
            futures.add(submitTask(callable));
        }

        for(Future<Response<?>> future : futures){
            try {
                results.add(future.get(TIMEOUT,TIME_UNIT));
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                fail();
            }
        }

        assertTrue(checkAllSuccess(results));

        for(Subscribe user : newUsers){
            int sessionNumber = cache.findSubscribe(user.getName()).getSessionNumber();
            assertEquals(sessionNumber,-1);
        }
        tearDownRegister();
        tearDownConnect();
    }

    /**
     * use case 3.2 - OpenStore
     * checks only one user opens a store successfully
     */
    @Test
    public void testOpenStoreSuccessOnce(){
        registerAndLoginUsers(users);
        StoreData storeToOpen = stores.get(0);

        List<Response<?>> results = new CopyOnWriteArrayList<>();
        List<Future<Response<?>>> futures = new CopyOnWriteArrayList<>();

        for(Subscribe user : newUsers){
            Callable<Response<?>> callable = ()->
                    logicManager.openStore(ids.get(user.getName()),storeToOpen);
            futures.add(submitTask(callable));
        }

        for(Future<Response<?>> future : futures){
            try {
                results.add(future.get(TIMEOUT,TIME_UNIT));
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                fail();
            }
        }

        assertTrue(checkOnlyOneSuccess(results));
        Store actualStore = daos.getStoreDao().find(storeToOpen.getName());
        assertNotNull(actualStore);

        tearDownOpenStore();
    }

    /**
     * use case 3.2 - OpenStore
     * checks only one user opens a store successfully
     * Assumes NUM_THREADS == NUM_STORES
     */
    @Test
    public void testOpenStoreSuccess(){
        registerAndLoginUsers(users);

        List<Response<?>> results = new CopyOnWriteArrayList<>();
        List<Future<Response<?>>> futures = new CopyOnWriteArrayList<>();

        for(int i=0;i<newUsers.size();i++){
            Subscribe user = newUsers.get(i);
            StoreData storeToOpen = stores.get(i);

            Callable<Response<?>> callable = ()->
                    logicManager.openStore(ids.get(user.getName()),storeToOpen);
            futures.add(submitTask(callable));
        }

        for(Future<Response<?>> future : futures){
            try {
                results.add(future.get(TIMEOUT,TIME_UNIT));
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                fail();
            }
        }

        assertTrue(checkAllSuccess(results));
        for(int i=0;i<newUsers.size();i++) {
            StoreData storeToOpen = stores.get(i);
            Store actualStore = daos.getStoreDao().find(storeToOpen.getName());
            assertNotNull(actualStore);
        }

        tearDownOpenStore();
    }

    /**
     * use case 3.5 - sendRequestToStore
     * checks that all users sends requests to store
     */
    @Test
    public void testSendRequestToStoreSuccess(){
        registerAndLoginUsers(users);
        StoreData storeToOpen = stores.get(0);
        openStore(admin,storeToOpen);

        List<Future<Response<?>>> futures = new CopyOnWriteArrayList<>();
        List<Response<?>> results = new CopyOnWriteArrayList<>();
        for(RequestData request : requests){
            if(request.getStoreName().equals(storeToOpen.getName())){
                Callable<Response<?>> callable = ()->
                        logicManager.addRequest(ids.get(request.getSenderName()),storeToOpen.getName(),request.getContent());
                futures.add(submitTask(callable));

            }
        }
        for(Future<Response<?>> future : futures) {
            try {
                results.add(future.get(TIMEOUT, TIME_UNIT));
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
                fail();
            }
        }
        assertTrue(checkAllSuccess(results));

        checkRequestsEqual(storeToOpen.getName(),requests);

        tearDownOpenStore();
    }

    @Test
    public void testAnswerRequestSuccessOnce(){
        StoreData storeToOpen = stores.get(0);
        registerLoginAndOpenStore(admin,users,storeToOpen);
        Subscribe opener = cache.findSubscribe(admin.getName());
        sendRequestsToStore(opener,storeToOpen);

        List<Future<Response<?>>> futures = new CopyOnWriteArrayList<>();
        List<Response<?>> results = new CopyOnWriteArrayList<>();

        Store actualStore = daos.getStoreDao().find(storeToOpen.getName());
        Map<Integer,Request> requests = actualStore.getRequests();

        for(Map.Entry<Integer,Request> request : requests.entrySet()){
            String comment = "c"+request.getKey();
            Callable<Response<?>> callable = ()->
                    logicManager.replayRequest(ids.get(request.getValue().getSenderName()),storeToOpen.getName(),request.getKey(),comment);
            futures.add(submitTask(callable));
        }

        for(Future<Response<?>> future : futures) {
            try {
                results.add(future.get(TIMEOUT, TIME_UNIT));
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
                fail();
            }
        }

        assertTrue(checkAllSuccess(results));

        actualStore = daos.getStoreDao().find(storeToOpen.getName());
        requests = actualStore.getRequests();

        for(Map.Entry<Integer,Request> request : requests.entrySet()){
            String comment = "c"+request.getKey();
            assertEquals(comment,request.getValue().getComment());
        }

    }

    /**
     * use case 4.3.1 - manageOwner
     * checks exactly one owner managed to add a new owner
     */
    @Test
    public void testManageOwnerSuccessOnce(){
        List<Subscribe> owners = users.subList(0,users.size()-1);
        StoreData storeToOpen = stores.get(0);
        Subscribe newOwner = users.get(users.size()-1);

        List<PermissionType> permissions = Collections.singletonList(PermissionType.ADD_OWNER);
        setUpAddManagerAndPermissions(admin,users,users,permissions,storeToOpen);

        List<Future<Response<?>>> futures = new CopyOnWriteArrayList<>();
        List<Response<?>> results = new CopyOnWriteArrayList<>();

        for(Subscribe owner : owners){
            Callable<Response<?>> callable = ()-> {
                Response<?> response = logicManager.manageOwner(ids.get(owner.getName()), storeToOpen.getName(), newOwner.getName());
                logicManager.approveManageOwner(ids.get(admin.getName()),storeToOpen.getName(),newOwner.getName());
                return response;
            };

            futures.add(submitTask(callable));
        }

        for(Future<Response<?>> future : futures) {
            try {
                results.add(future.get(TIMEOUT, TIME_UNIT));
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
                fail();
            }
        }
        assertTrue(checkOnlyOneSuccess(results));

        Store actualStore = daos.getStoreDao().find(storeToOpen.getName());
        boolean isOwner = actualStore.getPermissions().get(newOwner.getName()).isOwner();
        assertTrue(isOwner);

        tearDownOpenStore();
    }

    /**
     * use case 4.5 - addManager
     * checks exactly one manger managed to add a new manger
     */
    @Test
    public void testAddManagerSuccessOnce(){
        List<Subscribe> owners = users.subList(0,users.size()-1);
        StoreData storeToOpen = stores.get(0);
        Subscribe newManager = users.get(users.size()-1);

        List<PermissionType> permissions = Collections.singletonList(PermissionType.ADD_MANAGER);
        setUpAddManagerAndPermissions(admin,users,users.subList(0,users.size()-1),permissions,storeToOpen);

        List<Future<Response<?>>> futures = new CopyOnWriteArrayList<>();
        List<Response<?>> results = new CopyOnWriteArrayList<>();

        for(Subscribe owner : owners){
            Callable<Response<?>> callable = ()->
                    logicManager.addManager(ids.get(owner.getName()),newManager.getName(), storeToOpen.getName());

            futures.add(submitTask(callable));
        }

        for(Future<Response<?>> future : futures) {
            try {
                results.add(future.get(TIMEOUT, TIME_UNIT));
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
                fail();
            }
        }
        assertTrue(checkOnlyOneSuccess(results));

        Store actualStore = daos.getStoreDao().find(storeToOpen.getName());
        Permission permission = actualStore.getPermissions().get(newManager.getName());
        assertNotNull(permission);

        tearDownOpenStore();
    }


    /**
     * use case 4.1.1 -- add product to store
     * check that only one product is added when trying a lot of products with the same name
     */
    @Test
    public void addProductToStoreTestOneProduct(){
        StoreData storeToOpen = stores.get(0);
        List<PermissionType> permissionTypes=new ArrayList<>();
        permissionTypes.add(PermissionType.PRODUCTS_INVENTORY);
        Subscribe opener = cache.findSubscribe(admin.getName());
        setUpAddManagerAndPermissions(opener,users,users,permissionTypes,storeToOpen);
        ProductData productData=threadsData.getProductsPerStore().get(storeToOpen.getName()).get(0);

        List<Future<Response<?>>> futures = new CopyOnWriteArrayList<>();
        List<Response<?>> results = new CopyOnWriteArrayList<>();

        for(Subscribe sub:users){
            Callable<Response<?>> callable = ()->
                    logicManager.addProductToStore(ids.get(sub.getName()),productData);
            futures.add(submitTask(callable));
        }

        for(Future<Response<?>> future : futures) {
            try {
                results.add(future.get(TIMEOUT, TIME_UNIT));
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
                fail();
            }
        }
        assertTrue(checkOnlyOneSuccess(results));
        Store store=daos.getStoreDao().find(storeToOpen.getName());
        assertTrue(store.getProducts().containsKey(productData.getProductName()));
        tearDownOpenStore();
    }

    /**
     * use case 4.1.1 -- add product to store
     * check that all products were added when trying a lot of products with the different names
     */
    @Test
    public void addProductToStoreTestManyProducts(){
        StoreData storeToOpen = stores.get(0);
        List<PermissionType> permissionTypes=new ArrayList<>();
        permissionTypes.add(PermissionType.PRODUCTS_INVENTORY);
        Subscribe opener = cache.findSubscribe(admin.getName());
        setUpAddManagerAndPermissions(opener,users,users,permissionTypes,storeToOpen);

        List<Future<Response<?>>> futures = new CopyOnWriteArrayList<>();
        List<Response<?>> results = new CopyOnWriteArrayList<>();
        List<ProductData> products=productsPerStore.get(storeToOpen.getName());
        int i=0;
        for(Subscribe sub:users){
            ProductData product=products.get(i);
            Callable<Response<?>> callable = ()->
                    logicManager.addProductToStore(ids.get(sub.getName()),product);
            futures.add(submitTask(callable));
            i++;
        }

        for(Future<Response<?>> future : futures) {
            try {
                results.add(future.get(TIMEOUT, TIME_UNIT));
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
                fail();
            }
        }
        assertTrue(checkAllSuccess(results));
        Store store=daos.getStoreDao().find(storeToOpen.getName());
        for(ProductData productData: productsPerStore.get(storeToOpen.getName())) {
            assertTrue(store.getProducts().containsKey(productData.getProductName()));
        }
        tearDownOpenStore();
    }




    //------------------------------------------------setUp Methods----------------------------------------------------//
    @BeforeClass
    public static void beforeClass() {
        //TestMode();
        daos=new DaoHolder();
    }
    /**
     * inits the data and the logicManager
     */
    private void init(){
        threadsData = new TestDataThreads(NUM_THREADS,NUM_STORES,NUM_PRODUCTS);
        users = threadsData.getUsers();
        newUsers = users.subList(1,users.size());
        admin = users.get(0);
        ids = threadsData.getIds();
        stores = threadsData.getStores();
        productsPerStore = threadsData.getProductsPerStore();
        requests = threadsData.getRequests();

        threadPool = Executors.newFixedThreadPool(NUM_THREADS);
        try {
            logicManager = new LogicManager(admin.getName(), admin.getPassword(),
                    paymentSystem,supplySystem,daos, cache);
        } catch (Exception e) {
            fail();
        }
    }

    @Before
    @Transactional
    public void setUp() {
        daos = new DaoHolder();
        supplySystem = new ProxySupply();
        paymentSystem = new ProxyPayment();
        cache = new Cache();
        cache.resetList();
        init();
        publisher = new StubPublisher();
        SinglePublisher.initPublisher(publisher);
    }

    private void connect(){
        logicManager.connectToSystem();
    }
    private void setUpConnect(){
        for(int i=0;i<ids.size();i++)
            connect();
    }

    /**
     * opens a store, register and login users and add permissions to certain users
     * @param opener - the user who opens the store
     * @param users - the users to register and login
     * @param usersToAddPermissions - user to add permissions to them
     * @param premissions - the permissions to add to the users
     * @param storeToOpen - the store to open
     */
    private void setUpAddManagerAndPermissions(Subscribe opener, List<Subscribe> users,List<Subscribe> usersToAddPermissions, List<PermissionType> premissions, StoreData storeToOpen){
        registerLoginAndOpenStore(opener,users,storeToOpen);
        int id=ids.get(opener.getName());
        for(Subscribe sub : usersToAddPermissions){
            if(!sub.getName().equals(opener.getName())){
                logicManager.addManager(id,sub.getName(),storeToOpen.getName());
                logicManager.addPermissions(id,premissions,storeToOpen.getName(),sub.getName());
            }
        }
    }

    /**
     * register and logins users, opens a store
     * @param opener - the user who opens the store
     * @param users - users to register and login
     * @param storeToOpen - the store to open
     */
    private void registerLoginAndOpenStore(Subscribe opener, List<Subscribe> users, StoreData storeToOpen) {
        registerAndLoginUsers(users);
        openStore(opener,storeToOpen);
    }
    //-----------------------------------------------------------------------------------------------------------------//

    //---------------------------------------------tearDown Methods----------------------------------------------------//
    private void tearDownConnect(){
        cache.resetList();
        ids = new HashMap<>();
    }

    private void tearDownRegister(){
        removeSubscribes(users);
        tearDownConnect();
    }

    private void tearDownLogin(){
        logoutUsers(users);
        tearDownRegister();
    }

    //TODO change
    @Test
    public void tearDownOpenStore(){
        removeStores(stores);
        tearDownLogin();
    }

    @After
    public void tearDown(){
        threadPool.shutdown();
    }
    //-----------------------------------------------------------------------------------------------------------------//

    //-----------------------------------------------Helper Methods----------------------------------------------------//
    /**
     * submits a task to the thread pool
     * @param task - the task to submit to the thread pool
     * @param <T> - the return type given from executing the task
     * @return - Future<T> where T is the return type given from executing the task
     */
    private <T> Future<T> submitTask(Callable<T> task){
        return threadPool.submit(task);
    }

    /**
     * submits tasks to the thread pool
     * @param tasks - the tasks to submit to the thread pool
     * @param <T> - the return type given from executing a task
     * @return - Future<T> List where T is the return type given from executing a task
     */
    private <T> List<Future<T>> submitTasks (List<Callable<T>> tasks){
        List<Future<T>> results = new ArrayList<>();
        for(Callable<T> task : tasks)
            results.add(submitTask(task));
        return results;
    }

    /**
     * checks all the results returned with Success OpCode
     * @param results - the results to examine
     * @return - true if all the results are with Success OpCode, false otherwise
     */
    private boolean checkAllSuccess(List<Response<?>> results) {
        for(Response<?> response : results) {
            if (response.getReason() != OpCode.Success)
                return false;
        }
        return true;
    }

    /**
     * checks exactly one result is with Success OpCode in results
     * @param results - the results to examine
     * @return - true if exactly one result is with Success OpCode in results, false otherwise
     */
    private boolean checkOnlyOneSuccess(List<Response<?>> results) {
        boolean onlyOne = false;

        for(Response<?> response : results){
            if(response.getReason() == OpCode.Success)
                if(onlyOne)
                    return false;
                else
                    onlyOne = true;
        }
        return onlyOne;
    }

    private void registerUser(Subscribe user){
        logicManager.register(user.getName(),user.getPassword());
    }

    private void registerUsers(List<Subscribe> users){
        for (Subscribe user : users)
            registerUser(user);
    }

    private void loginUser(Subscribe user){
        logicManager.login(ids.get(user.getName()),user.getName(),user.getPassword());
    }

    private void loginUsers(List<Subscribe> users){
        for(Subscribe user : users)
            loginUser(user);
    }

    private void registerAndLogin(Subscribe user){
        registerUser(user);
        connect();
        loginUser(user);
    }

    private void registerAndLoginUsers(List<Subscribe> users){
        for(Subscribe user : users)
            registerAndLogin(user);
    }

    private void logoutUser(Subscribe user){
        logicManager.logout(ids.get(user.getName()));
    }

    private void logoutUsers(List<Subscribe> users) {
        for (Subscribe user : users)
            logoutUser(user);
    }

    private void removeSubscribe(Subscribe subscribe){
        daos.getSubscribeDao().remove(subscribe.getName());
    }

    private void removeSubscribes(List<Subscribe> subscribes){
        for (Subscribe s: subscribes) {
            removeSubscribe(s);
        }
    }

    private void removeStore(StoreData store){
        daos.getStoreDao().removeStore(store.getName());
    }

    private void removeStores(List<StoreData> stores){
        for (StoreData store: stores)
            removeStore(store);
    }

    private void openStore(Subscribe user, StoreData storeToOpen) {
        logicManager.openStore(ids.get(user.getName()),storeToOpen);
    }

    private void checkRequestsEqual(String storeName, List<RequestData> requests) {
        Store store = daos.getStoreDao().find(storeName);
        assertNotNull(store);

        for(Request request : store.getRequests().values()) {
            assertTrue(findRequest(request, requests));
            Subscribe s = cache.findSubscribe(request.getSenderName());
            assertNotNull(s);
            assertTrue(findRequestSubscribe(request,s.getRequests()));
        }
    }

    private boolean findRequestSubscribe(Request request, List<Request> requests) {
        for(Request requestData : requests)
            if(requestData.getStoreName().equals(request.getStoreName()) &&
                    requestData.getSenderName().equals(request.getSenderName())&&
                    requestData.getContent().equals(request.getContent())) {
                return true;
            }
        return false;
    }

    private boolean findRequest(Request request, List<RequestData> requests) {
        for(RequestData requestData : requests)
            if(requestData.getStoreName().equals(request.getStoreName()) &&
                    requestData.getSenderName().equals(request.getSenderName())&&
                    requestData.getContent().equals(request.getContent())) {
                return true;
            }
        return false;
    }

    private void sendRequestsToStore(Subscribe manager, StoreData storeToOpen) {
        for(RequestData request : requests)
            if(request.getStoreName().equals(storeToOpen.getName())) {
                logicManager.addManager(manager.getSessionNumber(),request.getSenderName(),storeToOpen.getName());
                logicManager.addRequest(ids.get(request.getSenderName()), storeToOpen.getName(), request.getContent());
            }
    }
    //-----------------------------------------------------------------------------------------------------------------//
}
