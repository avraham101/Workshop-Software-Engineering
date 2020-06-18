package Persitent.DaoProxy;

import DataAPI.StoreData;
import Domain.Store;
import Persitent.Dao.StoreDao;
import Persitent.DaoInterfaces.IStoreDao;

import java.util.List;

public class StoreDaoProxy implements IStoreDao {

    private StoreDao dao;

    public StoreDaoProxy(){
        this.dao = new StoreDao();
    }

    @Override
    public boolean addStore(Store store) {
        try{
            return dao.addStore(store);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public Store find(String storeName) {
        try{
            return dao.find(storeName);
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Store> getAll() {
        try{
            return dao.getAll();
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean removeStore(String name) {
        try{
            return dao.removeStore(name);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public void update(Store store) {
        try{
            dao.update(store);
        }catch (Exception e){}
    }

}
