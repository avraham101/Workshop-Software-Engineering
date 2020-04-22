package Stubs;

import Domain.Subscribe;

public class AdminStub extends SubscribeStub {

    public AdminStub(String userName, String password) {
        super(userName, password);
    }

    /**
     * use case 6.4.1
     * @return
     */
    @Override
    public boolean canWatchStoreHistory(String storeName) {
        return true;
    }

    /**
     * use case 6.4.2
     * @return
     */
    @Override
    public boolean canWatchUserHistory() {
        return true;
    }
}
