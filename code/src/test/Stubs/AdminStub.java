package Stubs;

import Domain.Subscribe;

public class AdminStub extends SubscribeStub {

    public AdminStub(String userName, String password) {
        super(userName, password);
    }

    @Override
    public boolean canWatchStoreHistory(String storeName) {
        return true;
    }

    @Override
    public boolean canWatchUserHistory() {
        return true;
    }
}
