package pl.com.psl.spring.hateoas.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.com.psl.spring.hateoas.service.entity.Booking;
import pl.com.psl.spring.hateoas.service.entity.Room;
import pl.com.psl.spring.hateoas.service.repository.BookingRepository;
import pl.com.psl.spring.hateoas.service.repository.RoomRepository;

import javax.annotation.PostConstruct;
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
                new Room("Cellar", "Small cellar without windows"),
                new Room("Small room", "Small room with balcony and nice view"),
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

    public Room getRoom(Long roomId) throws BookingServiceException {
        LOG.info("Getting room by roomId={}...", roomId);
        Room room = roomRepository.findOne(roomId);
        LOG.info("Got room={}", room);
        if (room == null) {
            throw new BookingServiceException("Room with roomId=" + roomId + " does not exist", BookingServiceException.ErrorCode.DOES_NOT_EXIST);
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

    public List<Booking> getBookings(Long roomId) throws BookingServiceException {
        LOG.info("Getting bookings by roomId={}...", roomId);
        Room room = getRoom(roomId);
        List<Booking> bookings = bookingRepository.findByRoomId(room.getRoomId());
        LOG.info("Got bookings={}", bookings);
        return bookings;
    }

    public Booking getBooking(Long bookingId) throws BookingServiceException {
        LOG.info("Getting booking by bookingId={}...", bookingId);
        Booking booking = bookingRepository.findOne(bookingId);
        LOG.info("Got booking={}", booking);
        if (booking == null) {
            throw new BookingServiceException("Booking with bookingId=" + bookingId + " does not exist", BookingServiceException.ErrorCode.DOES_NOT_EXIST);
        }
        return booking;
    }

    public Booking createBooking(Booking newBooking) throws BookingServiceException {
        LOG.info("Creating booking={}...", newBooking);
        Room room = getRoom(newBooking.getRoomId());
        List<Booking> bookings = getBookings(room.getRoomId());
        try {
            bookings.stream()
                    .filter(booking -> Objects.equals(booking.getDate(), newBooking.getDate()))
                    .findFirst()
                    .ifPresent(booking -> {
                        throw new IllegalArgumentException("Room with roomId=" + booking.getRoomId() + " is not available on date=" + booking.getDate());
                    });
        } catch (IllegalArgumentException e) {
            throw new BookingServiceException(e, BookingServiceException.ErrorCode.CONFILICT);
        }
        Booking booking = bookingRepository.save(newBooking);
        LOG.info("Crated booking={}", booking);
        return booking;
    }

    public Booking deleteBooking(Long bookingId) throws BookingServiceException {
        LOG.info("Deleting booking with bookingId={}...", bookingId);
        Booking booking = bookingRepository.findOne(bookingId);
        if (booking == null) {
            throw new BookingServiceException("Booking with bookingId=" + bookingId + " does not exist", BookingServiceException.ErrorCode.DOES_NOT_EXIST);
        }
        bookingRepository.delete(booking);
        LOG.info("Deleted booking with bookingId={}", bookingId);
        return booking;
    }
}
