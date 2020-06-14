package Systems.PaymentSystem;

import DataAPI.PaymentData;
import Systems.HttpConnection;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;

public class ExternalPayment extends PaymentSystem{

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
    public boolean pay(PaymentData paymentData) {
        String url = "https://cs-bgu-wsep.herokuapp.com/";
        String method = "POST";
        String params = "action_type=pay";
        LocalDate now= LocalDate.now();
        params += "&card_number="+paymentData.getCreditCard();
        params += "&month="+now.getMonthValue();
        params += "&year="+now.getYear();
        params += "&holder="+paymentData.getName();
        params += "&ccv="+paymentData.getCvv();
        params += "&id="+paymentData.getId();
        String msg = "";
        HttpConnection httpConnection = new HttpConnection();
        String result = httpConnection.send(url,method,params,msg);
        try{
            int transactionId=Integer.parseInt(result);
            if(transactionId==-1)
                return false;
            paymentData.setTransactionId(transactionId);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean cancel(PaymentData paymentData) {
        String url = "https://cs-bgu-wsep.herokuapp.com/";
        String method = "POST";
        String params = "action_type=cancel_pay";
        params += "&transaction_id="+paymentData.getTransactionId();
        String msg = "";
        HttpConnection httpConnection = new HttpConnection();
        String result = httpConnection.send(url,method,params,msg);
        System.out.println(result);
        try{
            int transactionId=Integer.parseInt(result);
            return transactionId!=-1;
        }
        catch (Exception e){
            return false;
        }
    }

}
