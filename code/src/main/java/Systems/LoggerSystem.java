package Systems;

import java.io.IOException;
import java.util.logging.*;

public class LoggerSystem {
    private final static Logger EVENT_LOGGER = Logger.getLogger(LoggerSystem.class.getName()); // logger for the events
    private final static Logger ERROR_LOGGER = Logger.getLogger(LoggerSystem.class.getName()); // logger for the errors

    /**
     * constructor
     * @throws IOException -  if there are IO problems opening the files
     */
    public LoggerSystem() throws IOException {
        FileHandler fileHandlerEvents = new FileHandler("Workshop202Events.%u.%g.txt");
        FileHandler fileHandlerError = new FileHandler("Workshop202Errors.%u.%g.txt");
        setHandlersFormat(fileHandlerEvents, fileHandlerError);
        EVENT_LOGGER.addHandler(fileHandlerEvents);
        ERROR_LOGGER.addHandler(fileHandlerError);
    }

    /**
     * getter of the event logger
     * @return - the EVENT_LOGGER
     */
    public Logger getEventLogger() {
        return EVENT_LOGGER;
    }

    /**
     * getter of the error logger
     * @return - the ERROR_LOGGER
     */
    public Logger getErrorLogger() {
        return ERROR_LOGGER;
    }

    /**
     * set the formats of the handlers
     * @param fileHandlerEvents - the handler of the events
     * @param fileHandlerErrors - the handler of the errors
     */
    private void setHandlersFormat(FileHandler fileHandlerEvents, FileHandler fileHandlerErrors) {
        fileHandlerEvents.setFormatter(new SimpleFormatter());
        fileHandlerErrors.setFormatter(new SimpleFormatter());
    }

}
