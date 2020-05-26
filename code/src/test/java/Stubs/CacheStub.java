package Stubs;

import Domain.User;
import Persitent.Cache;

public class CacheStub extends Cache {


    @Override
    public User findUser(int id) {
        User user=super.findUser(id);
        if(user!=null)
            return new UserStub();
        return null;
    }

}
