package api;

import com.github.javafaker.Faker;
import entities.CustomResponse;
import entities.RequestBody;
import org.junit.Assert;
import org.junit.Test;
import utilities.APIRunner;

import java.util.HashMap;
import java.util.Map;

public class SellerTests {

    @Test
    public void getSellers(){
        String path = "/api/myaccount/sellers/1087";
        APIRunner.runGET(path);
        System.out.println(APIRunner.getCustomResponse().getResponseBody());
        System.out.println(APIRunner.getCustomResponse().getSeller_name());
        System.out.println(APIRunner.getCustomResponse().getEmail());
    }

    @Test
    public void getSellersList(){
        String path = "/api/myaccount/sellers";
        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 50);
        APIRunner.runGET(path, params);
        int counter = 0;
        for (CustomResponse cr: APIRunner.getCustomResponse().getResponses()){
            System.out.println(cr.getCompany_name());
            counter++;
        }
        System.out.println("total: " + counter);
    }


    @Test
    public void createNewSeller(){
        String path = "/api/myaccount/sellers";
        RequestBody body = new RequestBody();
        body.setCompany_name("Meta");
        body.setSeller_name("Zuckerberg");
        body.setEmail("zuck@fb.com");
        body.setPhone_number("439820482");
        APIRunner.runPOST(path, body);
        System.out.println(APIRunner.getCustomResponse().getResponseBody());
    }

    @Test
    public void singleSellerCreation(){
        String pathForPost = "/api/myaccount/sellers";
        Faker faker = new Faker();
        String companyName = faker.company().name();
        String sellerName = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String phone = faker.phoneNumber().phoneNumber();

        RequestBody body = new RequestBody();
        body.setCompany_name(companyName);
        body.setSeller_name(sellerName);
        body.setEmail(email);
        body.setPhone_number(phone);

        APIRunner.runPOST(pathForPost, body);

        int id = APIRunner.getCustomResponse().getSeller_id();
        String urlForGet = "/api/myaccount/sellers/" + id;
        APIRunner.runGET(urlForGet);

        Assert.assertEquals(companyName, APIRunner.getCustomResponse().getCompany_name());
        Assert.assertEquals(sellerName, APIRunner.getCustomResponse().getSeller_name());
        Assert.assertEquals(email, APIRunner.getCustomResponse().getEmail());
        Assert.assertEquals(phone, APIRunner.getCustomResponse().getPhone_number());


    }
}
