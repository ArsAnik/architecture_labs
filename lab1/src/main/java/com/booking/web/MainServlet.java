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
  
}
