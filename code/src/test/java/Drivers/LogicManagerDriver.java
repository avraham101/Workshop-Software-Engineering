package Drivers;

import Domain.LogicManager;
import Persitent.Cache;
import Persitent.DaoHolders.DaoHolder;
import Systems.PaymentSystem.ProxyPayment;
import Systems.SupplySystem.ProxySupply;

public class LogicManagerDriver extends LogicManager {

    public LogicManagerDriver() throws Exception {
        super("Admin", "Admin", new ProxyPayment(), new ProxySupply(), new DaoHolder(),
                new Cache());
    }
}