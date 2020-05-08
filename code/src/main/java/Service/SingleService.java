package Service;

import Publisher.Publisher;
import ch.qos.logback.core.joran.event.SaxEventRecorder;

public class SingleService {

    private static ServiceAPI service;
    private static Publisher pub;

    public static ServiceAPI getInstance(){
        return service;
    }
    public static ServiceAPI getInstance(String name, String password) {
        try {
            service= new ServiceAPI(name,password);
            service.setPublisher(pub);
        } catch (Exception e) {
            return null;
        }
        return service;
    }

    public static void setPublisher(Publisher publ) {
        pub=publ;
    }
}
