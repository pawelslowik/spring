package pl.com.psl.spring.hateoas.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.com.psl.spring.hateoas.service.BookingServiceException;

/**
 * Created by psl on 15.08.17.
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    @ExceptionHandler(BookingServiceException.class)
    public ResponseEntity handleException(BookingServiceException e) {
        LOG.info("Handling exception={}", e.getMessage());
        if (BookingServiceException.ErrorCode.DOES_NOT_EXIST.equals(e.getErrorCode())) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        if (BookingServiceException.ErrorCode.CONFILICT.equals(e.getErrorCode())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }
}
