package Stubs;

import Domain.Subscribe;
import Persitent.DaoProxy.SubscribeDaoProxy;

public class StubSubscribeDao extends SubscribeDaoProxy {

    @Override
    public boolean update(Subscribe info) {
        return true;
    }
}
