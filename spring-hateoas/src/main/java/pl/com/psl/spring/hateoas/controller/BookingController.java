package pl.com.psl.spring.hateoas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.psl.spring.hateoas.service.entity.Booking;
import pl.com.psl.spring.hateoas.service.BookingService;
import pl.com.psl.spring.hateoas.service.BookingServiceException;
import pl.com.psl.spring.hateoas.service.entity.Room;

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
    public HttpEntity<ItemsResource<Room>> getRooms() throws BookingServiceException {
        List<Room> rooms = bookingService.getRooms();
        for (Room room : rooms) {
            room.add(linkTo(methodOn(BookingController.class).getRoom(room.getRoomId())).withSelfRel());
        }
        ItemsResource<Room> roomsResource = new ItemsResource<>(rooms);
        roomsResource.add(linkTo(methodOn(BookingController.class).getRooms()).withSelfRel());
        roomsResource.add(linkTo(methodOn(BookingController.class).getBookings()).withRel("bookings"));
        return new ResponseEntity<>(roomsResource, HttpStatus.OK);
    }

    @RequestMapping(path = "/rooms/{roomId}", method = RequestMethod.GET)
    public HttpEntity<Room> getRoom(@PathVariable Long roomId) throws BookingServiceException {
        Room room = bookingService.getRoom(roomId);
        room.add(linkTo(methodOn(BookingController.class).getRoom(room.getRoomId())).withSelfRel());
        room.add(linkTo(methodOn(BookingController.class).getBookings(room.getRoomId())).withRel("bookings"));
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @RequestMapping(path = "/rooms/{roomId}/bookings", method = RequestMethod.GET)
    public HttpEntity<ItemsResource<Booking>> getBookings(@PathVariable Long roomId) throws BookingServiceException {
        List<Booking> bookings = bookingService.getBookings(roomId);
        for (Booking booking : bookings) {
            booking.add(linkTo(methodOn(BookingController.class).getBooking(booking.getBookingId())).withSelfRel());
        }
        ItemsResource<Booking> bookingsResource = new ItemsResource<>(bookings);
        bookingsResource.add(linkTo(methodOn(BookingController.class).getBookings(roomId)).withSelfRel());
        return new ResponseEntity<>(bookingsResource, HttpStatus.OK);
    }

    @RequestMapping(path = "/rooms/bookings", method = RequestMethod.GET)
    public HttpEntity<ItemsResource<Booking>> getBookings() throws BookingServiceException {
        List<Booking> bookings = bookingService.getBookings();
        for (Booking booking : bookings) {
            booking.add(linkTo(methodOn(BookingController.class).getBooking(booking.getBookingId())).withSelfRel());
        }
        ItemsResource<Booking> bookingsResource = new ItemsResource<>(bookings);
        bookingsResource.add(linkTo(methodOn(BookingController.class).getBookings()).withSelfRel());
        return new ResponseEntity<>(bookingsResource, HttpStatus.OK);
    }

    @RequestMapping(path = "/rooms/bookings/{bookingId}", method = RequestMethod.GET)
    public HttpEntity<Booking> getBooking(@PathVariable Long bookingId) throws BookingServiceException {
        Booking booking = bookingService.getBooking(bookingId);
        booking.add(linkTo(methodOn(BookingController.class).getBooking(booking.getBookingId())).withSelfRel());
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @RequestMapping(path = "/rooms/{roomId}/bookings", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Booking> createBooking(@PathVariable Long roomId, @RequestBody Booking newBooking) throws BookingServiceException {
        Booking booking = bookingService.createBooking(new Booking(newBooking.getGuestName(), roomId, newBooking.getDate()));
        booking.add(linkTo(methodOn(BookingController.class).getBooking(booking.getBookingId())).withSelfRel());
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/rooms/bookings", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Booking> createBooking(@RequestBody Booking newBooking) throws BookingServiceException {
        Booking booking = bookingService.createBooking(newBooking);
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
