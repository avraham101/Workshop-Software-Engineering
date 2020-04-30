package Server.Controllers;

import DataAPI.ProductData;
import DataAPI.RequestData;
import DataAPI.Response;
import DataAPI.StoreData;
import Service.ServiceAPI;
import Service.SingleService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Response<Boolean> addStore(@RequestParam(name="id") int id, @RequestBody StoreData storeData){
        return SingleService.getInstance().openStore(id,storeData);

    }

    @PostMapping("request")
    @ResponseBody
    public Response<Boolean> writeRequestToStore(@RequestParam (name="id" ) int id, @RequestBody RequestData requestData){
        return SingleService.getInstance().writeRequestToStore(id,requestData.getStoreName(),requestData.getContent());
    }

    @GetMapping
    @ResponseBody
    public Response<List<StoreData>> getStores(){
       return SingleService.getInstance().viewStores();
    }

    @PostMapping("addproduct")
    @ResponseBody
    public Response<Boolean> addProduct(@RequestParam(name="id") int id, @RequestBody ProductData product){
        return SingleService.getInstance().addProductToStore(id,product);
    }
}
