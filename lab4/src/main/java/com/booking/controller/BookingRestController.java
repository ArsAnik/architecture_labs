package com.booking.controller;

import com.booking.entity.Booking;
import com.booking.entity.Client;
import com.booking.service.BookingService;
import com.booking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

@RestController
@RequestMapping("/api")
public class BookingRestController {

    @Autowired private BookingService bookingService;

    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/bookings/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable Integer id) {
        Booking booking = bookingService.getBookingById(id);
        return booking != null ? ResponseEntity.ok(booking) : ResponseEntity.notFound().build();
    }

    @PostMapping("/bookings")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        bookingService.saveBooking(booking);
        return ResponseEntity.ok(booking);
    }

    @PutMapping("/bookings/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Integer id, @RequestBody Booking booking) {
        Booking existing = bookingService.getBookingById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        
        existing.setCheckIn(booking.getCheckIn());
        existing.setCheckOut(booking.getCheckOut());
        existing.setRoomNumber(booking.getRoomNumber());
        existing.setStatus(booking.getStatus());
        
        bookingService.saveBooking(existing);
        return ResponseEntity.ok(existing);
    }

    @GetMapping(value = "/xml/bookings", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getAllBookingsXml() throws Exception {
        List<Booking> bookings = bookingService.getAllBookings();

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.registerModule(new Hibernate6Module());
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String xmlBody = xmlMapper.writer()
                .withRootName("bookings")
                .writeValueAsString(bookings);

        String fullXml =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<?xml-stylesheet type=\"text/xsl\" href=\"/bookings.xsl\"?>\n" +
            xmlBody;

        return ResponseEntity.ok()
                .header("Content-Type", "application/xml; charset=UTF-8")
                .body(fullXml);
    }
}