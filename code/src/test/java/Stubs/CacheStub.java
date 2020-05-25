package Stubs;

import Domain.User;
import Persitent.Cache;

public class CacheStub extends Cache {


    @Override
    public User findUser(int id) {
        return new UserStub(super.findUser(id));
    }

}
