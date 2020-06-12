package Systems.SupplySystem;

import DataAPI.DeliveryData;
import Systems.HttpConnection;

public class ExternalSupply extends SupplySystem {

    @Override
    public boolean connect() {
        String url = "https://cs-bgu-wsep.herokuapp.com/";
        String method = "POST";
        String params = "action_type=handshake";
        String msg = "";
        HttpConnection httpConnection = new HttpConnection();
        String result = httpConnection.send(url,method,params,msg);
        if(result!=null && result.equals("OK"))
            return true;
        return false;
    }

    @Override
    public boolean deliver(DeliveryData deliveryData) {
        String url = "https://cs-bgu-wsep.herokuapp.com/";
        String method = "POST";
        String params = "action_type=supply";
        params += "&name=";
        params += "&address="+deliveryData.getAddress();
        params += "&city=";
        params += "&country="+deliveryData.getCountry();
        params += "&zip=";
        String msg = "";
        HttpConnection httpConnection = new HttpConnection();
        String result = httpConnection.send(url,method,params,msg);
        System.out.println(result);
        if(result!=null) {
            int tmp = Integer.valueOf(result);
            if(tmp >= 10000 && tmp <= 100000) {
                return true;
            }
        }
        return false;
    }
}
