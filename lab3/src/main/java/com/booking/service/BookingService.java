package com.booking.service;

import com.booking.entity.Booking;
import com.booking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;

@Service
public class BookingService {
    
    @Autowired
    private BookingRepository bookingRepository;
    
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    
    public Booking getBookingById(Integer id) {
        return bookingRepository.findById(id).orElse(null);
    }
    
    public void saveBooking(Booking booking) {
        if (booking.getBookedAt() == null) {
            booking.setBookedAt(LocalDateTime.now());
        }
        bookingRepository.save(booking);
    }
}
