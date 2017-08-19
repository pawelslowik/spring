package pl.com.psl.spring.hateoas.service.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * Created by psl on 14.08.17.
 */
@Entity
public class Booking extends ResourceSupport{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookingId;
    private String guestName;
    private Long roomId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;


    @SuppressWarnings("unused")
    protected Booking() {
    }

    public Booking(String guestName, Long roomId, LocalDate date) {
        this.guestName = guestName;
        this.roomId = roomId;
        this.date = date;
    }

    public Booking(Long bookingId, String guestName, Long roomId, LocalDate date) {
        this(guestName, roomId, date);
        this.bookingId = bookingId;
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

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", guestName='" + guestName + '\'' +
                ", roomId=" + roomId +
                ", date=" + date +
                '}';
    }
}
