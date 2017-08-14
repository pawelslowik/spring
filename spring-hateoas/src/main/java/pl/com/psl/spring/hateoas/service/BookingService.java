package pl.com.psl.spring.hateoas.service;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Created by psl on 13.08.17.
 */
@Component
public class BookingService {

    private static final List<Room> ROOMS = Collections.unmodifiableList(Arrays.asList(
            new Room(1L, "Small room 1", "Small room without windows"),
            new Room(2L, "Small room 2", "Small room with balcony and nice view"),
            new Room(3L, "Medium room", "Medium room with balcony"),
            new Room(4L, "VIP room", "Luxurious room with a swimming pool")
    ));

    private List<Booking> bookings = Collections.synchronizedList(new ArrayList<>());

    public List<Room> getRooms() {
        //create new instance of Room, otherwise controller will keep adding links to the same instance
        return ROOMS.stream()
                .map(room -> new Room(room.getRoomId(), room.getName(), room.getDescription()))
                .collect(Collectors.toList());
    }

    public Room getRoom(Long roomId) {
        //create new instance of Room, otherwise controller will keep adding links to the same instance
        return ROOMS.stream()
                .filter(room -> Objects.equals(room.getRoomId(), roomId))
                .findFirst()
                .map(room -> new Room(room.getRoomId(), room.getName(), room.getDescription()))
                .orElseThrow(() -> new IllegalArgumentException("There's no room with roomId=" + roomId));
    }

    public List<Booking> getBookings() {
        //create new instance of Booking, otherwise controller will keep adding links to the same instance
        return bookings.stream()
                .map(booking -> new Booking(booking.getBookingId(), booking.getGuestName(), booking.getRoomId(), booking.getDate()))
                .collect(Collectors.toList());
    }

    public List<Booking> getBookings(Long roomId) {
        //create new instance of Booking, otherwise controller will keep adding links to the same instance
        return bookings.stream()
                .filter(booking -> Objects.equals(booking.getRoomId(), roomId))
                .map(booking -> new Booking(booking.getBookingId(), booking.getGuestName(), booking.getRoomId(), booking.getDate()))
                .collect(Collectors.toList());
    }

    public Booking getBooking(Long bookingId) {
        //create new instance of Booking, otherwise controller will keep adding links to the same instance
        return bookings.stream()
                .filter(booking -> Objects.equals(booking.getBookingId(), bookingId))
                .map(booking -> new Booking(booking.getBookingId(), booking.getGuestName(), booking.getRoomId(), booking.getDate()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There's no booking with bookingId=" + bookingId));
    }

    public Booking createBooking(Long roomId, String guestName, LocalDate date) {
        Room room = getRoom(roomId);
        List<Booking> bookings = getBookings(room.getRoomId());
        bookings.stream()
                .filter(booking -> Objects.equals(booking.getDate(), date))
                .findFirst()
                .ifPresent(booking -> {
                    throw new IllegalArgumentException("Room with roomId=" + booking.getRoomId() + " is not available on date=" + booking.getDate());
                });
        Booking booking = new Booking(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE), guestName, room.getRoomId(), date);
        this.bookings.add(booking);
        return booking;
    }
}
