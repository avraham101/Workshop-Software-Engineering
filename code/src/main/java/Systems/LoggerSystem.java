package Systems;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

public class LoggerSystem {

    private Object eventLock = new Object();
    private Object errorLock = new Object();
    private String pathEvents;
    private String pathErrors;
    private Loggy events;
    private Loggy errors;

    public LoggerSystem() {
        File file = new File(".\\src\\Logs");
        file.mkdirs();
        this.pathEvents = ".\\src\\Logs\\events.txt";
        this.pathErrors = ".\\src\\Logs\\errors.txt";
        createEvent();
        createError();
    }

    private void createEvent() {
        events = new Loggy(eventLock, pathEvents);
    }

    private void createError() {
        errors = new Loggy(errorLock, pathErrors);
    }

    public void writeEvent(String className, String methodName, String msg, Object[] params) {
        events.write(className, methodName, msg, params);
    }

    public void writeError(String className, String methodName, String msg, Object[] params) {
        errors.write(className, methodName, msg, params);
    }

}

class Loggy {

    private Object lock;
    private File file;

    public Loggy(Object lock, String path) {
        synchronized (lock) {
            this.lock = lock;
            this.file = new File(path);
            if(!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void write(String className, String methodName, String msg, Object[] params) {
        synchronized (lock) {
            try(FileOutputStream writer = new FileOutputStream(file,true)) {
                writer.write(printDate());
                writer.write(printHeader(className,methodName));
                writer.write(printParams(params));
                writer.write(printDes(msg));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] printDate() {
        LocalDateTime time = LocalDateTime.now();
        String output = time.toString() +"\n";
        return output.getBytes();
    }

    private byte[] printHeader(String className, String method) {
        String output = "Class: " + className;
        output += " Method: "+method+"\n";
        return output.getBytes();
    }

    private byte[] printParams(Object[] params) {
        String output = "params: ";
        if(params.length==0) {
            output = "No params";
            return output.getBytes();
        }
        for(int i=0;i<params.length; i++) {
            output += " ("+i+") ";
            if(params[i]==null)
                output+= "Null";
            else
                output += params[i].toString();
        }
        output += "\n";
        return output.getBytes();
    }

    private byte[] printDes(String des) {
        String output = "Description: ";
        output+=des +"\n";
        return output.getBytes();
    }
}
