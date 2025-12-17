package com.booking.ejb;
import com.booking.entity.Booking;
import jakarta.ejb.Stateless;
import java.util.List;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import com.booking.entity.BookingStatus;

@Stateless
public class BookingServiceBean {

  @PersistenceContext(unitName = "bookingPU")
  private EntityManager em;
  
  public List<Booking> getAllBookings() {
          return em.createQuery(
                  "SELECT b FROM Booking b LEFT JOIN FETCH b.client ORDER BY b.id DESC",
                  Booking.class
          ).getResultList();
  }
  
  public Booking getBookingById(Integer id) {
        return em.find(Booking.class, id);
  }

  public Booking saveBooking(Booking booking) {
      if (booking.getId() == null) {
          booking.setBookedAt(LocalDateTime.now());
          booking.setStatus(BookingStatus.created);
          em.persist(booking);
          return booking;
      } else {
          return em.merge(booking);
      }
  }
  
}
