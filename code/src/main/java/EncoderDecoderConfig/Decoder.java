package EncoderDecoderConfig;

import DataAPI.PermissionType;
import DataAPI.ProductData;
import DataAPI.StoreData;
import Service.ServiceAPI;
import Service.SingleService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class Decoder {

    private Gson gson;
    private Map<String,List<Map<String, Object>>> listMap;
    private HashMap<String, Integer> connected;
    private String idName;
    private ServiceAPI serviceAPI;

    public Decoder(String path) {
        gson = new Gson();
        listMap = new HashMap<>();
        connected = new HashMap<>();
        String json = readFile(path);
        Type type = new TypeToken<Map<String,List<Map<String, Object>>>>(){}.getType();
        listMap = gson.fromJson(json,type);
    }

    private String readFile(String path) {
        String output = "";
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                output += line;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    public void excecute() {
        List<Map<String,  Object>> functions = listMap.get("list");
        if(functions!=null) {
            for(Map<String, Object> func: functions) {
                Iterator<String> iterator = func.keySet().iterator();
                while (iterator.hasNext()) {
                    String nextKey = iterator.next();
                    executeFunc(nextKey, func);
                }
            }
            executeLogout();
        }
    }

    private void executeFunc(String key, Map<String, Object> data) {
        switch (key) {
            case "idName" : this.idName = (String)data.get(key); break;
            case "ServiceAPI": executeServiceAPI(data); break;
            case "register": executeRegister(data); break;
            case "openStore": exceuteOpenStore(data); break;
            case "addProductToStore": executeAddProductToStore(data); break;
            case "addManagerToStore": executeAddManagerToStore(data); break;
            case "addPermissions": executeAddPermissions(data); break;
        }
    }

    private void executeServiceAPI(Map<String, Object> data) {
        Map<String, Object> value = (Map<String, Object>)data.get("ServiceAPI");
        String userName = (String) value.get("userName");
        String password = (String) value.get("password");
        serviceAPI = SingleService.getInstance(userName, password);
    }

    private void executeRegister(Map<String, Object> data) {
        Map<String, Object> value = (Map<String, Object>)data.get("register");
        String userName = (String) value.get("userName");
        String password = (String) value.get("password");
        serviceAPI.register(userName,password);
        int id = serviceAPI.connectToSystem();
        connected.put(userName, id);
        serviceAPI.login(id,userName,password);
    }

    private void exceuteOpenStore(Map<String, Object> data) {
        String name = (String) data.get("idName");
        int id = connected.get(name);
        Map<String, Object> value = (Map<String, Object>)data.get("openStore");
        StoreData storeDetails = gson.fromJson(gson.toJson(value.get("storeDetails")),
                StoreData.class);
        serviceAPI.openStore(id,storeDetails);
    }

    private void executeAddProductToStore(Map<String, Object> data) {
        String name = (String) data.get("idName");
        int id = connected.get(name);
        Map<String, Object> value = (Map<String, Object>)data.get("addProductToStore");
        ProductData productData = gson.fromJson(gson.toJson(value.get("productData")),
                ProductData.class);
        serviceAPI.addProductToStore(id,productData);
    }

    private void executeAddManagerToStore(Map<String, Object> data) {
        String name = (String) data.get("idName");
        int id = connected.get(name);
        Map<String, Object> value = (Map<String, Object>)data.get("addManagerToStore");
        String storeName = (String) value.get("storeName");
        String userName = (String) value.get("userName");
        serviceAPI.addManagerToStore(id,storeName,userName);
    }

    private void executeAddPermissions(Map<String, Object> data) {
        String name = (String) data.get("idName");
        int id = connected.get(name);
        Map<String, Object> value = (Map<String, Object>)data.get("addPermissions");
        Type type = new TypeToken<List<PermissionType>>(){}.getType();
        List<PermissionType> permissions = gson.fromJson(gson.toJson(value.get("permissions")),type);
        String storeName = (String) value.get("storeName");
        String userName = (String) value.get("userName");
        serviceAPI.addPermissions(id, permissions, storeName, userName);
    }

    private void executeLogout() {
        for(Integer id: connected.values()) {
            serviceAPI.logout(id);
        }
    }

}
