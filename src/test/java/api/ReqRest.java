package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import utilities.Config;

public class ReqRest {

    public static void main(String[] args) {
        System.out.println(Config.getValue("username"));
        Response response = RestAssured.get("https://reqres.in/api/users/7");
        System.out.println(response.statusCode());
        String here = response.asString();

        String email = response.jsonPath().get("data.email").toString();
        String firstName = response.jsonPath().get("data.first_name").toString();
        String lastName = response.jsonPath().get("data.last_name").toString();
        String avatar = response.jsonPath().get("data.avatar").toString();

        System.out.println(email);
        System.out.println(firstName);
        System.out.println(lastName);
        System.out.println(avatar);

        Assert.assertFalse(email.isEmpty());
        Assert.assertFalse(firstName.isEmpty());
        Assert.assertFalse(lastName.isEmpty());
        Assert.assertFalse(avatar.isEmpty());

        Assert.assertTrue(email.endsWith("reqres.in"));
        Assert.assertTrue(avatar.endsWith(".png") || avatar.endsWith(".jpg"));

    }

}
