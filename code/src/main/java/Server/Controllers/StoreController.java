package Server.Controllers;

import DataAPI.ProductData;
import DataAPI.RequestData;
import DataAPI.Response;
import DataAPI.StoreData;
import Service.ServiceAPI;
import Service.SingleService;
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

    private  ServiceAPI serviceAPI;

    public StoreController() {
        this.serviceAPI = SingleService.getInstance();
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> addStore(@RequestParam(name="id") int id, @RequestBody StoreData storeData){
        Response<Boolean> response= SingleService.getInstance().openStore(id,storeData);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);

    }

    @PostMapping("request")
    @ResponseBody
    public ResponseEntity<?> writeRequestToStore(@RequestParam (name="id" ) int id, @RequestBody RequestData requestData){
        Response<Boolean> response = SingleService.getInstance().writeRequestToStore(id,requestData.getStoreName(),requestData.getContent());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getStores(){
       Response<List<StoreData>> response= SingleService.getInstance().viewStores();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }

    @PostMapping("addproduct")
    @ResponseBody
    public ResponseEntity<?> addProduct(@RequestParam(name="id") int id, @RequestBody ProductData product){
        Response<Boolean> response = SingleService.getInstance().addProductToStore(id,product);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }
}
