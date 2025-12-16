package com.booking.ejb;
import com.booking.entity.Booking;
import jakarta.ejb.Stateless;
import java.util.List;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;

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

}
