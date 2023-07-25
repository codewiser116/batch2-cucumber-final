package api;

import org.junit.Assert;
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
}
