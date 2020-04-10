package Systems;

import java.io.IOException;
import java.util.logging.*;

public class LoggerSystem {
    private final static Logger EVENT_LOGGER = Logger.getLogger("Event logger"); // logger for the events
    private final static Logger ERROR_LOGGER = Logger.getLogger("Error logger"); // logger for the errors

    /**
     * constructor
     * @throws IOException -  if there are IO problems opening the files
     */
    public  LoggerSystem() throws IOException {
            FileHandler fileHandlerEvents = new FileHandler("Workshop202Events.txt", 8096, 1, true);
            FileHandler fileHandlerError = new FileHandler("Workshop202Errors.txt", 8096, 1, true);
            setHandlersFormat(fileHandlerEvents, fileHandlerError);
            EVENT_LOGGER.addHandler(fileHandlerEvents);
            ERROR_LOGGER.addHandler(fileHandlerError);
            EVENT_LOGGER.setLevel(Level.ALL);

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
