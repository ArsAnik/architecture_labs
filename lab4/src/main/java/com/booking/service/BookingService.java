package com.booking.service;

import com.booking.entity.*;
import com.booking.event.AuditEvent;
import com.booking.repository.AuditChangeDBRepository;
import com.booking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BookingService {
    @Autowired private BookingRepository bookingRepository;
    @Autowired private AuditChangeDBRepository auditRepo;
    @Autowired private ApplicationEventPublisher eventPublisher;

    public List<Booking> getAllBookings() { return bookingRepository.findBookingsWithClient(); }
    public Booking getBookingById(Integer id) { return bookingRepository.findById(id).orElse(null); }

    public Booking saveBooking(Booking booking) {
        String action = booking.getId() == null ? "create" : "update";
        Map<String, Object> oldValues = Map.of();
        
        if (booking.getId() != null) {
            Booking existing = bookingRepository.findById(booking.getId()).orElse(null);
            if (existing != null) {
                oldValues = Map.of("status", existing.getStatus().name(), "room", existing.getRoomNumber(), "checkIn", existing.getCheckIn().toString(), "checkOut", existing.getCheckOut().toString());
            }
        }
        
        if (booking.getId() == null) {
            booking.setBookedAt(LocalDateTime.now());
            booking.setStatus(BookingStatus.created);
        }
        
        Booking saved = bookingRepository.save(booking);
        
        Map<String, Object> newValues = Map.of(
            "status", saved.getStatus().name(),
            "room", saved.getRoomNumber(),
            "checkIn", saved.getCheckIn().toString(),
            "checkOut", saved.getCheckOut().toString()
        );
        
        auditRepo.save(AuditChangeDB.create("booking", saved.getId(), action, oldValues, newValues));
        eventPublisher.publishEvent(new AuditEvent(this, "booking", saved.getId(), action, newValues));
        return saved;
    }
}
