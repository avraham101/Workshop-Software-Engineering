package Stubs;

import Domain.Subscribe;
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


    @Override
    public Subscribe findSubscribe(String userName) {
        if(userName==null||userName.equals("Store"))
            return null;
        return new Subscribe("yuv","al");
    }
}
