package api;

import tests.Ten;

import java.util.Map;

public class SomeTest {

    public static void main(String[] args) {
        Map<String, Object> response = Ten.createSeller("james bond", "123456");
        for (String s: response.keySet()){
            System.out.println(response.get(s));
        }

        Ten.getSellers("123456");
    }
}
