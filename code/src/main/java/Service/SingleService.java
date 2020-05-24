package Service;

import Publisher.Publisher;
import ch.qos.logback.core.joran.event.SaxEventRecorder;

public class SingleService {

    private static ServiceAPI service;

    public static ServiceAPI getInstance(){
        return service;
    }
    public static ServiceAPI getInstance(String name, String password) {
        try {
            service= new ServiceAPI(name,password);
        } catch (Exception e) {
            return null;
        }
        return service;
    }
}
