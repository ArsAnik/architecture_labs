package com.booking.web;

import com.booking.ejb.BookingServiceBean;
import com.booking.ejb.ClientServiceBean;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.booking.entity.Client;
import com.booking.entity.BookingStatus;
import com.booking.entity.Booking;

@WebServlet("/")
public class MainServlet extends HttpServlet {

  @EJB
  private BookingServiceBean bookingService;

  @EJB
  private ClientServiceBean clientService;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
      req.setAttribute("clients", clientService.getAllClients());
      req.setAttribute("bookings", bookingService.getAllBookings());
      req.getRequestDispatcher("/index.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws IOException, ServletException {
      req.setCharacterEncoding("UTF-8");
      resp.setContentType("text/html; charset=UTF-8");
      String action = req.getParameter("action");
            
      if ("addClient".equals(action)) {
          Client client = new Client();
          client.setName(req.getParameter("name"));
          client.setPhone(req.getParameter("phone"));
          client.setEmail(req.getParameter("email"));
          clientService.saveClient(client);

      } else if ("addBooking".equals(action)) {
          Integer clientId = Integer.valueOf(req.getParameter("clientId"));
          Client client = clientService.getClientById(clientId);

          Booking booking = new Booking();
          booking.setClient(client);
          booking.setCheckIn(java.sql.Date.valueOf(req.getParameter("checkIn")).toLocalDate());
          booking.setCheckOut(java.sql.Date.valueOf(req.getParameter("checkOut")).toLocalDate());
          booking.setRoomNumber(req.getParameter("roomNumber"));
          bookingService.saveBooking(booking);

      } else if ("editBooking".equals(action)) {
          Integer id = Integer.valueOf(req.getParameter("id"));
          Booking existing = bookingService.getBookingById(id);
          if (existing != null) {
              existing.setCheckIn(java.sql.Date.valueOf(req.getParameter("checkIn")).toLocalDate());
              existing.setCheckOut(java.sql.Date.valueOf(req.getParameter("checkOut")).toLocalDate());
              existing.setRoomNumber(req.getParameter("roomNumber"));
              existing.setStatus(BookingStatus.valueOf(req.getParameter("status")));
              bookingService.saveBooking(existing);
          }
      } else if ("deleteClient".equals(action)) {
          Integer id = Integer.valueOf(req.getParameter("id"));
          clientService.deleteClient(id);
      }

      resp.sendRedirect(req.getContextPath() + "/");
  }
  
}
