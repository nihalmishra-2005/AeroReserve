package com.airline.controller;

import com.airline.model.*;
import com.airline.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final UserService userService;

    @GetMapping("/{id}/confirmation")
    public String confirmation(@PathVariable Long id, Model model) {
        Booking booking = bookingService.findById(id).orElseThrow();
        model.addAttribute("booking", booking);
        return "booking/confirmation";
    }

    @GetMapping("/history")
    public String history(Authentication auth, Model model) {
        User user = userService.findByEmail(auth.getName()).orElseThrow();
        model.addAttribute("bookings", bookingService.getUserBookings(user.getId()));
        return "booking/history";
    }

    @PostMapping("/{id}/cancel")
    public String cancel(@PathVariable Long id, Authentication auth) {
        Booking booking = bookingService.findById(id).orElseThrow();
        User user = userService.findByEmail(auth.getName()).orElseThrow();
        // Ensure the booking belongs to this user
        if (booking.getUser().getId().equals(user.getId())) {
            bookingService.cancelBooking(id);
        }
        return "redirect:/bookings/history?cancelled=true";
    }
}
