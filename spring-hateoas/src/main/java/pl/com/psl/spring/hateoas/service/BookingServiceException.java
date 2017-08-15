package pl.com.psl.spring.hateoas.service;

/**
 * Created by psl on 15.08.17.
 */
public class BookingServiceException extends Exception {

    public enum ErrorCode {DOES_NOT_EXIST, CONFILICT, OTHER}

    private ErrorCode errorCode;

    public BookingServiceException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BookingServiceException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
