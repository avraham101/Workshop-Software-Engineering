package EncoderDecoderConfig;

import DataAPI.PermissionType;
import DataAPI.ProductData;
import DataAPI.StoreData;
import Persitent.Cache;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Encoder {

    private Gson gson;
    private List<String> functions;
    private Cache cache;

    public Encoder() {
        gson = new Gson();
        functions = new LinkedList<>();
        cache = new Cache();
    }

    public void encodeAdmin(String userName, String password) {
        String output = "{\n";
        output += "\t\"ServiceAPI\":\n";
        output += "\t{\n";
        output += "\t\t\"userName\":\n";
        output += "\t\t" + gson.toJson(userName)+"\n";
        output += "\t\t,\"password\":\n";
        output += "\t\t" + gson.toJson(password)+ "\n";
        output += "\t}\n";
        output += "}\n";
        functions.add(output);
    }

    public void encodeRegister(String userName, String password) {
        String output= "{\n";
        output += "\t\"register\":\n";
        output += "\t{\n";
        output += "\t\t\"userName\":\n";
        output += "\t\t"+gson.toJson(userName)+"\n";
        output += "\t\t,\"password\":\n";
        output += "\t\t"+gson.toJson(password)+"\n";
        output +="\t}\n";
        output +="}\n";
        functions.add(output);
    }

    public void encodeOpenStore(int id,StoreData storeDetails) {
        String idName = cache.findUser(id).getUserName();
        String output = "{\n";
        output += "\t\"openStore\":\n";
        output += "\t{\n";
        output += "\t\t\"idName\":\n";
        output += "\t\t"+gson.toJson(idName)+"\n";
        output += "\t\t,\"storeDetails\":\n";
        output += "\t\t"+gson.toJson(storeDetails)+"\n";
        output += "\t}\n";
        output += "}\n";
        functions.add(output);
    }

    public void endodeAddProductToStore(int id, ProductData productData) {
        String idName = cache.findUser(id).getUserName();
        String output = "{\n";
        output += "\t\"addProductToStore\":\n";
        output += "\t{\n";
        output += "\t\t\"idName\":\n";
        output += "\t\t"+gson.toJson(idName)+"\n";
        output += "\t\t,\"productData\":\n";
        output += "\t\t"+gson.toJson(productData)+"\n";
        output += "\t}\n";
        output += "}\n";
        functions.add(output);
    }

    public void encodeAddManagerToStore(int id, String storeName,String userName) {
        String idName = cache.findUser(id).getUserName();
        String output = "{\n";
        output += "\t\"addManagerToStore\":\n";
        output += "\t{\n";
        output += "\t\t\"idName\":\n";
        output += "\t\t"+gson.toJson(idName)+"\n";
        output += "\t\t,\"storeName\":\n";
        output += "\t\t"+gson.toJson(storeName)+"\n";
        output += "\t\t,\"userName\":\n";
        output += "\t\t"+gson.toJson(userName)+"\n";
        output += "\t}\n";
        output += "}\n";
        functions.add(output);
    }

    public void encodeAddPermissions(int id,List<PermissionType> permissions, String storeName, String userName) {
        String idName = cache.findUser(id).getUserName();
        String output = "{\n";
        output += "\t\"addPermissions\":\n";
        output += "\t{\n";
        output += "\t\t\"idName\":\n";
        output += "\t\t"+gson.toJson(idName)+"\n";
        output += "\t\t,\"permissions\":\n";
        output += "\t\t" + gson.toJson(permissions) +"\n";
        output += "\t\t,\"storeName\":\n";
        output += "\t\t" + gson.toJson(storeName) +"\n";
        output += "\t\t,\"userName\": \n";
        output += "\t\t" + gson.toJson(userName) + "\n";
        output += "\t}\n";
        output += "}\n";
        functions.add(output);
    }

    public String getFunctions() {
        String output = "{\"list:\"[\n";
        for(String func: functions) {
            output += func;
            output += ",";
        }
        output = output.substring(0, output.length()-1);
        output += "]}";
        return output;
    }

    public void saveFile() {
        String path = "./file.json";
        String res = getFunctions();
        try (FileOutputStream writer = new FileOutputStream(path)) {
            writer.write(res.getBytes());
            System.out.println(res);
        } catch (IOException e) {

        }
    }
}
