package Server.Controllers;

import DataAPI.*;
import Domain.Request;
import Service.SingleService;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * use case 4.3.2
     * @param id the id of the subscribe approving
     * @param storeName the store name of the approval
     * @param userName the user name of the approval
     * @return
     */
    @PostMapping("approve/owner")
    public ResponseEntity<?> approveOwnerToStore(@RequestParam(name="id" ) int id,
                                                 @RequestParam(name="store" ) String storeName,
                                                 @RequestParam(name="user" ) String userName) {
        System.out.println("in approve owner "+storeName);
        storeName = json.fromJson(storeName,String.class);
        Response<Boolean> response = SingleService.getInstance().approveManageOwner(id,storeName,userName);
        return getResponseEntity(response);
    }

    @PostMapping("approve/list")
    public ResponseEntity<?> getApprovedOwnersFromStore(@RequestParam(name="id" ) int id,
                                                        @RequestBody String storeName) {
        storeName = json.fromJson(storeName,String.class);
        Response<List<String>> response = SingleService.getInstance().getApprovedManagers(id,storeName);
        return getResponseEntity(response);
    }

    /**
     * use case 4.6.1 - add permissions
     */

    @PostMapping("permissions")
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
    @PostMapping("permissions/delete")
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

    @PostMapping("deleteManager")
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
        Response<List<RequestData>> response =SingleService.getInstance().watchRequestsOfStore(id,store);
        return getResponseEntity(response);

    }

    /**
     * use case 4.9.2 - reply request
     */

    @PostMapping("response/{store}")
    public  ResponseEntity<?> answerRequest(@PathVariable String store,@RequestParam (name="id" ) int id,
                                            @RequestBody String responseDataStr){
        ResponseData responseData = json.fromJson(responseDataStr,ResponseData.class);
        Response<RequestData> response = SingleService.getInstance().answerRequest(id,responseData.getRequestId(),
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
