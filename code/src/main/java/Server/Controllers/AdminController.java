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
        System.out.println("2");
        return "admin";
    }

    @PostMapping("/admin")
    public ResponseEntity<?> initialStart(@RequestHeader Map<String,String> header, @RequestBody String string){
        Gson json = new Gson();
        UserData user =  json.fromJson(string,UserData.class);
        System.out.println(user.getName());
        System.out.println(user.getPassword());
        Boolean state= SingleService.getInstance(user.getName(),user.getPassword()) !=null;

        Response<Boolean> response = new Response<>(state, OpCode.Success);
        //String body = json.toJson(response);
        //System.out.println(body);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.TEXT_PLAIN_VALUE);
        headers.add(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.name());
        headers.add(HttpHeaders.CONNECTION, "close");
        //headers.add(HttpHeaders.CONTENT_LENGTH, ""+body.length());
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "OPTIONS, GET, POST, PUT, PATCH, DELETE");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type, Authorization");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
        //return body;
    }
}
