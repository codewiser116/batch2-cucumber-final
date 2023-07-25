package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import utilities.CashwiseAuthorization;
import utilities.Config;
import java.util.HashMap;
import java.util.Map;

public class CashwiseSellersTests {

    @Test
    public void getSingleSeller(){
        int id = 65;
        String token = CashwiseAuthorization.getToken();
        String url = Config.getValue("cashwiseApiUrl") + "/api/myaccount/sellers/" + id;

        Response response = RestAssured.given().auth().oauth2(token).get(url);
        System.out.println(response.statusCode());
        response.prettyPrint();
    }

    @Test
    public void getAllSellers() throws JsonProcessingException {
        String token = CashwiseAuthorization.getToken();
        String url = Config.getValue("cashwiseApiUrl") + "/api/myaccount/sellers";
        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 35);
        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);
        System.out.println(response.statusCode());

        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        int size = customResponse.getResponses().size();
        for (int i = 0; i < size; i++){
            System.out.println(customResponse.getResponses().get(i).getEmail());
        }

    }

    @Test
    public void createSeller() throws JsonProcessingException {
        String token = CashwiseAuthorization.getToken();
        String url = Config.getValue("cashwiseApiUrl") + "/api/myaccount/sellers";
        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name("Sky diving");
        requestBody.setSeller_name("Josh");
        requestBody.setEmail("joshua5@sky.com");
        requestBody.setPhone_number("3489240240");
        requestBody.setAddress("Des Plaines, IL");

        Response response = RestAssured.given().auth().oauth2(token).
                contentType(ContentType.JSON).body(requestBody).post(url);

        ObjectMapper objectMapper = new ObjectMapper();
        response.prettyPrint();
        CustomResponse customResponse = objectMapper.readValue(response.asString(),
                CustomResponse.class);

        System.out.println(customResponse.getSeller_id());

        int id = customResponse.getSeller_id();
        String token2 = CashwiseAuthorization.getToken();
        String url2 = Config.getValue("cashwiseApiUrl") + "/api/myaccount/sellers/" + id;

        System.out.println(token2);
        System.out.println(url2);

        Response response1 = RestAssured.given().auth().oauth2(token2).get(url2);
        Assert.assertEquals(200, response1.statusCode());

    }

    @Test
    public void createManySellers(){
        String token = CashwiseAuthorization.getToken();
        String url = Config.getValue("cashwiseApiUrl") + "/api/myaccount/sellers";
        Faker faker = new Faker();

        for (int i = 0; i < 15; i++){
            RequestBody requestBody = new RequestBody();
            requestBody.setCompany_name(faker.company().name());
            requestBody.setSeller_name(faker.name().fullName());
            requestBody.setEmail(faker.internet().emailAddress());
            requestBody.setAddress(faker.address().fullAddress());
            requestBody.setPhone_number(faker.phoneNumber().phoneNumber());

            Response response = RestAssured.given().auth().oauth2(token).
                    contentType(ContentType.JSON).body(requestBody).post(url);

            System.out.println(response.statusCode());

        }
    }



}
