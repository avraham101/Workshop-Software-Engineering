package Server.Controllers;

import DataAPI.ProductData;
import DataAPI.RequestData;
import DataAPI.Response;
import DataAPI.StoreData;
import Domain.Request;
import Service.ServiceAPI;
import Service.SingleService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;

@Controller
@RequestMapping("store")
public class StoreController {

    Gson json ;

    public StoreController() {
        json = new Gson();
    }

    /**
     * use case 3.2 - Open Store
     */

    @PostMapping
        public ResponseEntity<?> addStore(@RequestParam(name="id") int id, @RequestBody String storeDataStr){
        StoreData storeData= json.fromJson(storeDataStr,StoreData.class);
        Response<Boolean> response= SingleService.getInstance().openStore(id,storeData);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);

    }

    /**
     * use case 3.5 - write a request to specific store
     */

    @PostMapping("request")
        public ResponseEntity<?> writeRequestToStore(@RequestParam (name="id" ) int id, @RequestBody String  requestDataStr){
        RequestData requestData =json.fromJson(requestDataStr,RequestData.class);
        Response<Boolean> response = SingleService.getInstance().writeRequestToStore(id,requestData.getStoreName(),requestData.getContent());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }

    /**
     * use 2.4.1 - show the details about every store
     */

    @GetMapping
        public ResponseEntity<?> getStores(){
       Response<List<StoreData>> response= SingleService.getInstance().viewStores();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }



}
