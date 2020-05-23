package Stubs;

import Domain.Permission;
import Domain.Store;
import Domain.Subscribe;
import Persitent.StoreDao;

import java.util.ArrayList;
import java.util.List;

public class StubStoreDao extends StoreDao {
    private final String password="'Yuval', 'ca2a39c3cc021ba7eddc404157ed9cd1306c90431f0050651340b61414dc8a92c83a11ddfa3c4044c920715566ade28b2a0b1a37ffe85dfedf69c40db21769c6', '1'";
    @Override
    public Store find(String storeName) {
        return new Store(storeName,
                new Permission(new Subscribe("Yuval", password)),"description");
    }

    @Override
    public List<Store> getAll() {
        List<Store> stores=new ArrayList<>();
        stores.add(new Store("Store",
                new Permission(new Subscribe("Yuval", password)),"description"));
        return stores;
    }
}
