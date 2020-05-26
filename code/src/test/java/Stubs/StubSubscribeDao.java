package Stubs;

import Domain.Subscribe;
import Persitent.SubscribeDao;

public class StubSubscribeDao extends SubscribeDao {

    @Override
    public boolean update(Subscribe info) {
        return true;
    }
}
