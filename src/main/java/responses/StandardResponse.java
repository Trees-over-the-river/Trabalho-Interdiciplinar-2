package responses;

public class StandardResponse {

    private StatusResponse status;
    private Object message;

    public StandardResponse(StatusResponse status, Object message) {
        this.status = status;
        this.message = message;
    }


    public StatusResponse getStatus() {
        return status;
    }

    public void setStatus(StatusResponse status) {
        this.status = status;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }


}
