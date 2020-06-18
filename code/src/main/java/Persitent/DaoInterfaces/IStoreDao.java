package Persitent.DaoInterfaces;

import DataAPI.StoreData;
import Domain.Store;

import java.util.List;

public interface IStoreDao {
    boolean addStore(Store store);
    Store find(String storeName);
    List<Store> getAll();
    boolean removeStore(String name);
    void update(Store store);
}
