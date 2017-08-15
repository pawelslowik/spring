package pl.com.psl.spring.hateoas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.psl.spring.hateoas.service.entity.Booking;
import pl.com.psl.spring.hateoas.service.BookingService;
import pl.com.psl.spring.hateoas.service.BookingServiceException;
import pl.com.psl.spring.hateoas.service.entity.Room;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by psl on 13.08.17.
 */
@RestController
@RequestMapping("/")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @RequestMapping(path = "/rooms", method = RequestMethod.GET)
    public HttpEntity<List<Room>> getRooms() throws BookingServiceException {
        List<Room> rooms = bookingService.getRooms();
        for (Room room : rooms) {
            room.add(linkTo(methodOn(BookingController.class).getRoom(room.getRoomId())).withSelfRel());
        }
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @RequestMapping(path = "/rooms/{roomId}", method = RequestMethod.GET)
    public HttpEntity<Room> getRoom(@PathVariable Long roomId) throws BookingServiceException {
        Room room = bookingService.getRoom(roomId);
        room.add(linkTo(methodOn(BookingController.class).getRoom(room.getRoomId())).withSelfRel());
        room.add(linkTo(methodOn(BookingController.class).getBookings(room.getRoomId())).withRel("bookings"));
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @RequestMapping(path = "/rooms/{roomId}/bookings", method = RequestMethod.GET)
    public HttpEntity<List<Booking>> getBookings(@PathVariable Long roomId) throws BookingServiceException {
        List<Booking> bookings = bookingService.getBookings(roomId);
        for (Booking booking : bookings) {
            booking.add(linkTo(methodOn(BookingController.class).getBooking(booking.getBookingId())).withSelfRel());
        }
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @RequestMapping(path = "/rooms/bookings", method = RequestMethod.GET)
    public HttpEntity<List<Booking>> getBookings() throws BookingServiceException {
        List<Booking> bookings = bookingService.getBookings();
        for (Booking booking : bookings) {
            booking.add(linkTo(methodOn(BookingController.class).getBooking(booking.getBookingId())).withSelfRel());
        }
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @RequestMapping(path = "/rooms/bookings/{bookingId}", method = RequestMethod.GET)
    public HttpEntity<Booking> getBooking(@PathVariable Long bookingId) throws BookingServiceException {
        Booking booking = bookingService.getBooking(bookingId);
        booking.add(linkTo(methodOn(BookingController.class).getBooking(booking.getBookingId())).withSelfRel());
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @RequestMapping(path = "/rooms/{roomId}/bookings", method = RequestMethod.POST)
    public HttpEntity<Booking> createBooking(@PathVariable Long roomId, @RequestBody BookingRequest bookingRequest) throws BookingServiceException {
        Booking booking = bookingService.createBooking(roomId, bookingRequest.getGuestName(), LocalDate.parse(bookingRequest.getDate()));
        booking.add(linkTo(methodOn(BookingController.class).getBooking(booking.getBookingId())).withSelfRel());
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/rooms/{roomId}/bookings/{bookingId}", method = RequestMethod.DELETE)
    public HttpEntity<Booking> deleteBooking(@PathVariable Long roomId, @PathVariable Long bookingId) throws BookingServiceException {
        return deleteBooking(bookingId);
    }

    @RequestMapping(path = "/rooms/bookings/{bookingId}", method = RequestMethod.DELETE)
    public HttpEntity<Booking> deleteBooking(@PathVariable Long bookingId) throws BookingServiceException {
        Booking booking = bookingService.deleteBooking(bookingId);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }
}
