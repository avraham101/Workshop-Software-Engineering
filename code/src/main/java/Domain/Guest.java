package Domain;

import DataAPI.StoreData;

public class Guest extends UserState {

    @Override
    public boolean login(User user, Subscribe subscribe) {
        user.setState(subscribe);
        return true;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean logout(User user) {
        return false;
    }

    /**
     * use case 3.2
     * @param storeDetails - the details of the store
     * @return always null. guest cant open store.
     */
    @Override
    public Store openStore(StoreData storeDetails) {
        return null;
    }
}
