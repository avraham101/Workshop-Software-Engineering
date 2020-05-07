package Server.Controllers;

import DataAPI.ManagerData;
import DataAPI.Response;
import DataAPI.ResponseData;
import DataAPI.StoreData;
import Domain.Purchase;
import Domain.Request;
import Service.SingleService;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Signature;
import java.util.List;

@RestController
@RequestMapping("managers")
public class ManagerController {

    Gson json ;

    public ManagerController(Gson json) {
        this.json = json;
    }

    /**
     * use case 4.3
     */

    @PostMapping("owner")
    public ResponseEntity<?> addOwnerToStore(@RequestParam(name="id" ) int id,
                                               @RequestBody String managerDataStr ){
        ManagerData managerData = json.fromJson(managerDataStr,ManagerData.class);
        Response<Boolean> response = SingleService.getInstance().manageOwner(id,managerData.getStoreName(),managerData.getUserName());
        return getResponseEntity(response);


    }

    /**
     * use case 4.5- add manager to store
     */
    @PostMapping("manager")
    public ResponseEntity<?> addManagerToStore(@RequestParam(name="id" ) int id,
                                               @RequestBody String managerDataStr ){
        ManagerData managerData = json.fromJson(managerDataStr,ManagerData.class);
        Response<Boolean> response = SingleService.getInstance().addManagerToStore(id,managerData.getStoreName(),managerData.getUserName());
        return getResponseEntity(response);
    }

    /**
     * use case 4.6.1 - add permissions
     */

    @PutMapping("permissions")
    public ResponseEntity<?> addPermission(@RequestParam(name="id" ) int id,
                                           @RequestBody String managerDataStr){
        ManagerData managerData = json.fromJson(managerDataStr,ManagerData.class);
        Response<Boolean> response = SingleService.getInstance().addPermissions(id,managerData.getPermissions(),
                            managerData.getStoreName(),managerData.getUserName());
        return getResponseEntity(response);

    }

    /**
     * use case 4.6.2 - remove permissions
     */
    @DeleteMapping("permissions")
    public ResponseEntity<?> deletePermissions(@RequestParam(name="id" ) int id,
                                               @RequestBody String managerDataStr){
        ManagerData managerData = json.fromJson(managerDataStr,ManagerData.class);
        Response<Boolean> response = SingleService.getInstance().removePermissions(id,
                managerData.getPermissions(),
                managerData.getStoreName(),managerData.getUserName());
        return getResponseEntity(response);

    }
    /**
     * use case 4.7 - remove manger
     */

    @DeleteMapping("manager")
    public ResponseEntity<?> deleteManager(@RequestParam(name="id" ) int id,
                                           @RequestBody String managerDataStr){
        ManagerData managerData = json.fromJson(managerDataStr,ManagerData.class);
        Response<Boolean> response = SingleService.getInstance().removeManager(id,managerData.getUserName()
                ,managerData.getStoreName());
        return getResponseEntity(response);

    }

    /**
     * use case 4.9.1 - watch request
     */

    @GetMapping("request/{store}")
    public ResponseEntity<?> getRequests(@PathVariable String store, @RequestParam (name="id" ) int id){
        Response<List<Request>> response =SingleService.getInstance().watchRequestsOfStore(id,store);
        return getResponseEntity(response);

    }

    /**
     * use case 4.9.2 - reply request
     */

    @PutMapping("response/{store}")
    public  ResponseEntity<?> answerRequest(@PathVariable String store,@RequestParam (name="id" ) int id,
                                            @RequestBody String responseDataStr){
        ResponseData responseData = json.fromJson(responseDataStr,ResponseData.class);
        Response<Request> response = SingleService.getInstance().answerRequest(id,responseData.getRequestId(),
                responseData.getContent(),store);
        return getResponseEntity(response);

    }

    @GetMapping("mystores")
    public ResponseEntity<?> getStoresManagedByUser(@RequestParam (name="id" ) int id){
        Response<List<StoreData>> response = SingleService.getInstance().getStoresManagedByUser(id);
        return getResponseEntity(response);
    }

    @GetMapping("mymanagers/{store}")
    public ResponseEntity<?> getManagersOfStore(@PathVariable String store){
        Response<List<String>> response = SingleService.getInstance().getManagersOfStore(store);
        return getResponseEntity(response);

    }

    /**
     * use case 4.10 - watch Store History by store owner
     */

    @GetMapping("history/{store}")
    public ResponseEntity<?>  getStoreHistory(@PathVariable String store, @RequestParam (name="id" ) int id){
        Response<List<Purchase>> response = SingleService.getInstance().watchStoreHistory(id,store);
        return getResponseEntity(response);
    }



    private ResponseEntity<?> getResponseEntity(Response<?> response) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }



}
