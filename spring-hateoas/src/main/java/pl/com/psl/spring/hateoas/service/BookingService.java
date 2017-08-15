package pl.com.psl.spring.hateoas.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by psl on 13.08.17.
 */
@Component
public class BookingService {

    private static final Logger LOG = LoggerFactory.getLogger(BookingService.class);

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @PostConstruct
    public void init() {
        LOG.info("Initializing DB...");
        Arrays.asList(
                new Room("Small room 1", "Small room without windows"),
                new Room("Small room 2", "Small room with balcony and nice view"),
                new Room("Medium room", "Medium room with balcony"),
                new Room("VIP room", "Luxurious room with a swimming pool")
        ).forEach(roomRepository::save);
        LOG.info("DB initialized!");
    }

    public List<Room> getRooms() {
        LOG.info("Getting all rooms...");
        Iterable<Room> foundRooms = roomRepository.findAll();
        LOG.info("Got room={}", foundRooms);
        List<Room> rooms = new ArrayList<>();
        foundRooms.forEach(rooms::add);
        return rooms;
    }

    public Room getRoom(Long roomId) {
        LOG.info("Getting room by roomId={}", roomId);
        Room room = roomRepository.findOne(roomId);
        LOG.info("Got room={}", room);
        if (room == null) {
            throw new IllegalArgumentException("There's no room with roomId=" + roomId);
        }
        return room;
    }

    public List<Booking> getBookings() {
        LOG.info("Getting all bookings...");
        Iterable<Booking> foundBookings = bookingRepository.findAll();
        LOG.info("Got bookings={}", foundBookings);
        List<Booking> bookings = new ArrayList<>();
        foundBookings.forEach(bookings::add);
        return bookings;
    }

    public List<Booking> getBookings(Long roomId) {
        LOG.info("Getting bookings by roomId={}", roomId);
        List<Booking> bookings = bookingRepository.findByRoomId(roomId);
        LOG.info("Got bookings={}", bookings);
        return bookings;
    }

    public Booking getBooking(Long bookingId) {
        LOG.info("Getting booking by bookingId={}", bookingId);
        Booking booking = bookingRepository.findOne(bookingId);
        LOG.info("Got booking={}", booking);
        if (booking == null) {
            throw new IllegalArgumentException("There's no booking with bookingId=" + bookingId);
        }
        return booking;
    }

    public Booking createBooking(Long roomId, String guestName, LocalDate date) {
        LOG.info("Creating booking with roomId={}, guestName={} and date={}", roomId, guestName, date);
        Room room = getRoom(roomId);
        List<Booking> bookings = getBookings(room.getRoomId());
        bookings.stream()
                .filter(booking -> Objects.equals(booking.getDate(), date))
                .findFirst()
                .ifPresent(booking -> {
                    throw new IllegalArgumentException("Room with roomId=" + booking.getRoomId() + " is not available on date=" + booking.getDate());
                });
        Booking booking = bookingRepository.save(new Booking(guestName, room.getRoomId(), date));
        LOG.info("Crated booking={}", booking);
        return booking;
    }
}
