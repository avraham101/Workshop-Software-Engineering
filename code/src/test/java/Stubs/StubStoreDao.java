package Stubs;

import Domain.Permission;
import Domain.Store;
import Domain.Subscribe;
import Persitent.DaoProxy.StoreDaoProxy;

import java.util.ArrayList;
import java.util.List;

public class StubStoreDao extends StoreDaoProxy {
    private final String password="'Yuval', 'ca2a39c3cc021ba7eddc404157ed9cd1306c90431f0050651340b61414dc8a92c83a11ddfa3c4044c920715566ade28b2a0b1a37ffe85dfedf69c40db21769c6', '1'";
    private final String storeName ="Store";
    private  Store store=new StoreStub(storeName,
                new Permission(new Subscribe("Yuval", password)),"description");
    @Override
    public Store find(String name) {
        if(name=="Store")
            return store;
        return null;
    }

    @Override
    public List<Store> getAll() {
        List<Store> stores=new ArrayList<>();
        stores.add(new Store("Store",
                new Permission(new Subscribe("Yuval", password)),"description"));
        return stores;
    }

    @Override
    public boolean addStore(Store store) {
        return true;
    }

    @Override
    public boolean removeStore(String name) {
        return false;
    }

    @Override
    public void update(Store store) {
        this.store=store;
    }
}
