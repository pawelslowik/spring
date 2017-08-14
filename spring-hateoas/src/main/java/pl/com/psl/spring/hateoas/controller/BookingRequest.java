package pl.com.psl.spring.hateoas.controller;

/**
 * Created by psl on 14.08.17.
 */
public class BookingRequest {

    private String guestName;
    private String date;

    public String getGuestName() {
        return guestName;
    }

    public String getDate() {
        return date;
    }
}
