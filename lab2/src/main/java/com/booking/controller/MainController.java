package com.booking.controller;

import com.booking.entity.Booking;
import com.booking.entity.Client;
import com.booking.service.BookingService;
import com.booking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class MainController {
    
    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private ClientService clientService;
    
    @GetMapping
    public String listBookings(Model model) {
        model.addAttribute("clients", clientService.getAllClients());
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "index";
    }
    
    @PostMapping("/add/client")
    public String addClient(@ModelAttribute Client client) {
        clientService.saveClient(client);
        return "redirect:/";
    }
    
    @PostMapping("/add/booking")
    public String saveBooking(@ModelAttribute Booking booking) {
        bookingService.saveBooking(booking);
        return "redirect:/";
    }
    
    @PostMapping("/edit/booking/{id}")
    public String editBooking(@PathVariable Integer id, @ModelAttribute Booking booking) {
        Booking existingBooking = bookingService.getBookingById(id);
        if (existingBooking != null) {
            existingBooking.setCheckIn(booking.getCheckIn());
            existingBooking.setCheckOut(booking.getCheckOut());
            existingBooking.setRoomNumber(booking.getRoomNumber());
            existingBooking.setStatus(booking.getStatus());
            bookingService.saveBooking(existingBooking);
        }
        return "redirect:/";
    }
    
    @PostMapping("/delete/client/{id}")
    public String deleteClient(@PathVariable Integer id) {
        clientService.deleteClient(id);
        return "redirect:/";
    }
}
