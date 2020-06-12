package Systems.PaymentSystem;

import DataAPI.PaymentData;
import Systems.HttpConnection;

public class ExternalPayment extends PaymentSystem{

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
    public boolean pay(PaymentData paymentData) {
        String url = "https://cs-bgu-wsep.herokuapp.com/";
        String method = "POST";
        String params = "action_type=pay";
        params += "&card_number="+paymentData.getCreditCard();
        params += "&month=";
        params += "&year=";
        params += "&holder=";
        params += "&ccv=";
        params += "&id=";
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

    @Override
    public boolean cancel(PaymentData paymentData) {
        String url = "https://cs-bgu-wsep.herokuapp.com/";
        String method = "POST";
        String params = "action_type=cancel_pay";
        params += "&transaction_id=";
        String msg = "";
        HttpConnection httpConnection = new HttpConnection();
        String result = httpConnection.send(url,method,params,msg);
        System.out.println(result);
        if(result!=null) {
            int tmp = Integer.valueOf(result);
            if(tmp == 1) {
                return true;
            }
        }
        return false;
    }

}
