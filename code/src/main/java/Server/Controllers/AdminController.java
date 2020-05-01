package Server.Controllers;

import DataAPI.OpCode;
import DataAPI.Response;
import DataAPI.UserData;
import Domain.Purchase;
import Service.SingleService;
import com.google.gson.Gson;
import com.sun.org.apache.xpath.internal.axes.SelfIteratorNoPredicate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

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

    @PostMapping
    public ResponseEntity<?> initialStart(@RequestBody String string){
        Gson json = new Gson();
        UserData user =  json.fromJson(string,UserData.class);
        System.out.println(user.getName());
        System.out.println(user.getPassword());
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

    private ResponseEntity<?> getResponseEntity(Response<?> response) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }
}
