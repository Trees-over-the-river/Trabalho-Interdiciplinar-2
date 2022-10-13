package responses;

public class StandardSuccessResponse extends StandardResponse {
    public StandardSuccessResponse(Object message) {
        super(StatusResponse.SUCCESS, message);
    }

}
