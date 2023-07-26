package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import utilities.CashwiseAuthorization;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import utilities.Config;

import java.util.List;

public class CashwiseBankAccountTests {

    @Test
    public void getAllBankAccounts(){
        String token = CashwiseAuthorization.getToken();
        Response response = RestAssured.given().auth().oauth2(token).
                get(Config.getValue("cashwiseApiUrl")+"/api/myaccount/bankaccount");
        System.out.println(response.statusCode());

        int size = response.jsonPath().getInt("$.size()");
        System.out.println(size);

        for (int i = 0; i < size; i++){
            String bankAccountName1 = response.jsonPath().getString("[" + i + "].bank_account_name");
            String accountType = response.jsonPath().getString("[" + i + "].type_of_pay");
            String balance = response.jsonPath().getString("[" + i + "].balance");
            Assert.assertFalse("Bank account name is empty: " + i,bankAccountName1.trim().isEmpty());
            Assert.assertFalse("Bank type is empty: " + i, accountType.trim().isEmpty());
            Assert.assertFalse("Balance is empty: " + i, balance.trim().isEmpty());

        }
    }

    @Test
    public void getAllAccounts() throws JsonProcessingException {
        String token = CashwiseAuthorization.getToken();
        String url = Config.getValue("cashwiseApiUrl") + "/api/myaccount/bankaccount";

        Response response = RestAssured.given().auth().oauth2(token).get(url);

        ObjectMapper mapper = new ObjectMapper();
        CustomResponse[] customResponse = mapper.readValue(response.asString(), CustomResponse[].class);
        boolean flag = false;

        for (int i = 0; i < customResponse.length; i++){
            if (customResponse[i].getBalance() < 1000){
                flag = true;
                String id = customResponse[i].getId();
                String url2 = Config.getValue("cashwiseApiUrl") + "/api/myaccount/bankaccount/" + id;
                Response response1 = RestAssured.given().auth().oauth2(token).delete(url2);
                Assert.assertEquals(200, response1.statusCode());
            }
        }
        if (!flag){
            System.out.println("No accounts were deleted");
        }


    }
}
