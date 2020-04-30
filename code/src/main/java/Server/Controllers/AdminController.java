package Server.Controllers;

import DataAPI.OpCode;
import DataAPI.Response;
import DataAPI.UserData;
import Service.SingleService;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Controller
public class AdminController {

    @GetMapping("/admin")
    @ResponseBody
    public String getAdminPage(){
        return "admin";
    }

    /**
     * use case 1.1
     */

    @PostMapping("/admin")
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
}
