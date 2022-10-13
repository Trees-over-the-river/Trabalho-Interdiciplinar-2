package responses;

public class StandardErrorResponse extends StandardResponse {
    public StandardErrorResponse(Object message) {
        super(StatusResponse.ERROR, message);
    }

}
