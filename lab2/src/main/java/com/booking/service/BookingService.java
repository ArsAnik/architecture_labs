package com.booking.service;

import com.booking.entity.Booking;
import com.booking.entity.BookingStatus;
import com.booking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findBookingsWithClient();
    }

    public Booking getBookingById(Integer id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public Booking saveBooking(Booking booking) {
        if (booking.getId() == null) {
            booking.setBookedAt(LocalDateTime.now());
            booking.setStatus(BookingStatus.created);
        }
        return bookingRepository.save(booking);
    }
}
