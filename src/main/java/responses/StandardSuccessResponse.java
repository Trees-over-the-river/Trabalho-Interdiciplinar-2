package responses;

import com.google.gson.JsonElement;

public class StandardSuccessResponse extends StandardResponse {
    public StandardSuccessResponse(String message) {
        super(StatusResponse.SUCCESS, message);
    }

    public StandardSuccessResponse(JsonElement data) {
        super(StatusResponse.SUCCESS, data);
    }
}
