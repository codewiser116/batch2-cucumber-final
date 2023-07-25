package api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomResponse {

    private int category_id;
    private String created;
    private int seller_id;
    private List<CustomResponse> responses;
    private String email;

}
