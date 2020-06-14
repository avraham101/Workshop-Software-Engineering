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
        return result != null && result.equals("OK");
    }

    @Override
    public boolean deliver(DeliveryData deliveryData) {
        String url = "https://cs-bgu-wsep.herokuapp.com/";
        String method = "POST";
        String params = "action_type=supply";
        params += "&name="+deliveryData.getName();
        params += "&address="+deliveryData.getAddress();
        params += "&city="+deliveryData.getCity();
        params += "&country="+deliveryData.getCountry();
        params += "&zip="+deliveryData.getZip();
        String msg = "";
        HttpConnection httpConnection = new HttpConnection();
        String result = httpConnection.send(url,method,params,msg);
        System.out.println(result);
        try{
            int transactionId=Integer.parseInt(result);
            if(transactionId==-1)
                return false;
            deliveryData.setTransactionId(transactionId);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }
}
