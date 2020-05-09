package Server.Controllers;

import DataAPI.RequestData;
import DataAPI.Response;
import DataAPI.StoreData;
import Service.SingleService;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController  {

    Gson json ;

    public StoreController() {
    json=new Gson();
       // json = builderDiscount.create();
    }

    /**
     * use case 3.2 - Open Store
     */

    @PostMapping()
        public ResponseEntity<?> addStore(@RequestParam(name="id") int id, @RequestBody String storeDataStr){
        StoreData storeData= json.fromJson(storeDataStr,StoreData.class);
        Response<Boolean> response= SingleService.getInstance().openStore(id,storeData);
        return getResponseEntity(response);
    }



    /**
     * use case 3.5 - write a request to specific store
     */

    @PostMapping("request")
        public ResponseEntity<?> writeRequestToStore(@RequestParam (name="id" ) int id, @RequestBody String  requestDataStr){
        RequestData requestData =json.fromJson(requestDataStr,RequestData.class);
        Response<Boolean> response = SingleService.getInstance().writeRequestToStore(id,requestData.getStoreName(),requestData.getContent());
        return getResponseEntity(response);
    }

    /**
     * use case 4.2.1.1 - add discount to store
     */

    @PostMapping("/discount")
    public ResponseEntity<?> addDiscountToStore(@RequestParam (name="id" ) int id,
                                                @RequestParam (name="store") String store,
                                                @RequestBody String discount){
        Response<Boolean> response = SingleService.getInstance().addDiscount(id,discount,store);
        return getResponseEntity(response);
    }

    /**
     * use 2.4.1 - show the details about every store
     */

    @GetMapping()
        public ResponseEntity<?> getStores(){
        Response<List<StoreData>> response= SingleService.getInstance().viewStores();
        return getResponseEntity(response);
    }

    /**
     * 4.2.1.2 - remove discount
     */
    @PostMapping("discount/delete")
    public ResponseEntity<?> deleteDiscountFromStore(@RequestParam (name="store") String store,
                                                     @RequestParam (name="id") int id,
                                                     @RequestBody String discountId){
        int disId=json.fromJson(discountId,Integer.class);
        Response<Boolean> response =  SingleService.getInstance().deleteDiscountFromStore(id,disId,store);
        return getResponseEntity(response);
    }

    /**
     * 4.2.1.3 - view discounts
     */

    @GetMapping("discount/get")
    public ResponseEntity<?> getDiscounts(@RequestParam (name="store") String store){
        Response<HashMap<Integer,String>> response = SingleService.getInstance().viewDiscounts(store);
       return getResponseEntity(response);
    }

    /**
     * use case 4.2.2.1 - update policy of a store
     */

    @PostMapping("policy/add")
    public ResponseEntity<?> updatePolicy( @RequestParam (name="store") String store,
                                           @RequestParam (name="id" ) int id,
                                           @RequestBody String policyData){
        Response<Boolean> response = SingleService.getInstance().upadtePolicy(id,policyData,store);
        return getResponseEntity(response);
    }

    /**
     * use case 4.2.2.2 - view the policy of a store
     */
    @GetMapping("policy/{store}")
        public ResponseEntity<?> getStorePolicy(@PathVariable String store){
        Response<String> response = SingleService.getInstance().viewPolicy(store);
        return getResponseEntity(response);
    }


        private ResponseEntity<?> getResponseEntity(Response<?> response) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }

}
