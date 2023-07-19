package api;

import utilities.CashwiseAuthorization;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import utilities.Config;

public class CashwiseBankAccount {

    @Test
    public void getAllBankAccounts(){
        String token = CashwiseAuthorization.getToken();
        Response response = RestAssured.given().auth().oauth2(token).
                get(Config.getValue("cashwiseApiUrl")+"/api/myaccount/bankaccount");
        System.out.println(response.statusCode());
        response.prettyPrint();
    }
}
