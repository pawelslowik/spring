package pl.com.psl.spring.hateoas.service;

import org.springframework.hateoas.ResourceSupport;
import java.time.LocalDate;

/**
 * Created by psl on 14.08.17.
 */
public class Booking extends ResourceSupport{

    private Long bookingId;
    private String guestName;
    private Long roomId;
    private LocalDate date;

    public Booking(Long bookingId, String guestName, Long roomId, LocalDate date) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.roomId = roomId;
        this.date = date;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public String getGuestName() {
        return guestName;
    }

    public Long getRoomId() {
        return roomId;
    }

    public LocalDate getDate() {
        return date;
    }
}
