package Systems;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnection {

    public String send(String path, String method, String params, String msg) {
        byte[] bParams = params.getBytes();
        byte[] bMsg = msg.getBytes();
        int len = bParams.length + bMsg.length;
        try {
            URL url = new URL(path);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setFixedLengthStreamingMode(len);
            http.setRequestMethod(method);
            http.setDoOutput(true);
            http.connect();
            OutputStream outputStream = http.getOutputStream();
            outputStream.write(bParams);
            outputStream.write(bMsg);
            InputStream inputStream = http.getInputStream();
            int next=0;
            String output = "";
            do {
                next = inputStream.read();
                if(next!=-1) {
                    output += (char)next;
                }
                //System.out.println((char)next);
            }while (next!=-1);
            return output;
        } catch (Exception e) {

        }
        return null;
    }
}
