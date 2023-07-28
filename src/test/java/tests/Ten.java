package tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ten {

    private static List<String> db = new ArrayList<>();

    public static void getSellers(String token){
        if (token.equals("123456")){
            for (String s: db){
                System.out.println(s);
            }
        }else{
            System.out.println("403");
            System.out.println("Unauthorized");
        }
    }


    public static Map<String, Object> createSeller(String sellerName, String token){
            if (token.equals("123456")){
                if (sellerName.length() > 5){
                    Map<String, Object> response = new HashMap<>();
                    response.put("status code", 200);
                    response.put("body", "response body some json format");
                    return response;
                }else {
                    Map<String, Object> response = new HashMap<>();
                    response.put("status code", 400);
                    response.put("body", "error message about wrong request body");
                    return response;
                }
            }else {
                Map<String, Object> response = new HashMap<>();
                response.put("status code", 403);
                response.put("body", "unauthorized user");
                return response;
            }


    }
}
