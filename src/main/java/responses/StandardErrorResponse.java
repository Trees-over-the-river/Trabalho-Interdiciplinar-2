package responses;

import com.google.gson.JsonElement;

public class StandardErrorResponse extends StandardResponse {

    private String message;
    private JsonElement data;

    public StandardErrorResponse(String message) {
        super(StatusResponse.ERROR, message);
    }

    public StandardErrorResponse(JsonElement data) {
        super(StatusResponse.ERROR, data);
    }
}
