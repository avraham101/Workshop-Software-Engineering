package Server.Controllers;

import DataAPI.RequestData;
import DataAPI.Response;
import DataAPI.StoreData;
import Service.ServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;

@Controller
@RequestMapping("store")
public class StoreController {

    private  ServiceAPI serviceAPI;

    @Autowired
    public StoreController() {
        try {
            this.serviceAPI = new ServiceAPI("admin","admin");
        } catch (Exception e) {

        }
    }

    @PostMapping
    @ResponseBody
    public Response<Boolean> addStore(@RequestParam(name="id") int id, @RequestBody StoreData storeData){
        return serviceAPI.openStore(id,storeData);

    }

    @PostMapping
    @ResponseBody
    public Response<Boolean> writeRequestToStore(@RequestParam (name="id" ) int id, @RequestBody RequestData requestData){
        return serviceAPI.writeRequestToStore(id,requestData.getStoreName(),requestData.getContent());
    }

    @GetMapping
    @ResponseBody
    public Response<List<StoreData>> getStores(){
       return serviceAPI.viewStores();
    }
}
