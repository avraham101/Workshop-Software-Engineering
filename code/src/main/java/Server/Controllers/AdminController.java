package Server.Controllers;

import DataAPI.OpCode;
import DataAPI.Response;
import DataAPI.UserData;
import Domain.Purchase;
import Service.SingleService;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminController {

    @GetMapping
    @ResponseBody
    public String getAdminPage(){
        return "admin";
    }

    /**
     * use case 1.1
     */

    @PostMapping("init")
    public ResponseEntity<?> initialStart(@RequestBody String string){
        Gson json = new Gson();
        UserData user =  json.fromJson(string,UserData.class);
        Boolean state = SingleService.getInstance(user.getName(),user.getPassword()) !=null;
        Response<Boolean> response = new Response<>(state, OpCode.Success);
        //send the response
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }

    /**
     * use case 6.4.1 - admin watch history purchases of some user
     */
    @GetMapping("userhistory/{user}")
    public ResponseEntity<?> getUserHistory(@RequestParam(name="id") int id ,@PathVariable String user){
        Response<List<Purchase>> response = SingleService.getInstance().AdminWatchUserPurchasesHistory(id,user);
        return getResponseEntity(response);

    }

    /**
     * use case 6.4.2 - admin watch history purchases of some store
     */

    @GetMapping("storehistory/{store}")
    public ResponseEntity<?> getStoreHistory(@RequestParam(name="id") int id ,@PathVariable String store){
        Response<List<Purchase>> response = SingleService.getInstance().AdminWatchStoreHistory(id,store);
        return getResponseEntity(response);
    }

    /**
     * get all the users for the admin
     * @param id - the id of the admin
     * @return - list of all the names of the users
     */
    @GetMapping("allusers")
    public ResponseEntity<?> getAllUsers(@RequestParam(name="id") int id ) {
        Response<List<String>> response = SingleService.getInstance().getAllUsers(id);
        return getResponseEntity(response);
    }

    private ResponseEntity<?> getResponseEntity(Response<?> response) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }
}
