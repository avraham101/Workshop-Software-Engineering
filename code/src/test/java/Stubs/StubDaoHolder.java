package Stubs;

import Persitent.DaoHolders.DaoHolder;
import Persitent.DaoInterfaces.IStoreDao;
import Persitent.DaoInterfaces.ISubscribeDao;

public class StubDaoHolder extends DaoHolder {

    private StubStoreDao storeDao;
    private StubSubscribeDao subscribeDao;

    public StubDaoHolder() {
        storeDao=new StubStoreDao();
        subscribeDao=new StubSubscribeDao();
    }

    @Override
    public IStoreDao getStoreDao() {
        return storeDao;
    }

    @Override
    public ISubscribeDao getSubscribeDao() {
        return subscribeDao;
    }
}
