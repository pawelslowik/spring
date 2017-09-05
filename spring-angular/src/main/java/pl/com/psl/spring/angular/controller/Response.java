package pl.com.psl.spring.angular.controller;

/**
 * Created by psl on 05.09.17.
 */
public class Response {

    private Object payload;
    private String message;

    public Response(Object payload, String message) {
        this.payload = payload;
        this.message = message;
    }

    public Response(String message) {
        this.message = message;
    }

    public Object getPayload() {
        return payload;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Response{" +
                "payload=" + payload +
                ", message='" + message + '\'' +
                '}';
    }
}
