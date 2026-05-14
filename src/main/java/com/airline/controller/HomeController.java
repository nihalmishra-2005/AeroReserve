package com.airline.controller;

import com.airline.model.Booking;
import com.airline.model.User;
import java.util.List;
import com.airline.service.BookingService;
import com.airline.service.FlightService;
import com.airline.service.UserService;
import lombok.RequiredArgsConstructor;

import java.security.Principal;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final FlightService flightService;
    private final BookingService bookingService;
    private final UserService userService;

    @GetMapping("/")
    public String home() {
        return "redirect:/auth/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {

        User user = userService.findByEmail(principal.getName()).orElseThrow();

        List<Booking> bookings = bookingService.getUserBookings(user.getId());

        long confirmedCount = bookings.stream()
                .filter(b -> b.getStatus().name().equals("CONFIRMED"))
                .count();

        long cancelledCount = bookings.stream()
                .filter(b -> b.getStatus().name().equals("CANCELLED"))
                .count();

        model.addAttribute("user", user);
        model.addAttribute("bookings", bookings);
        model.addAttribute("confirmedCount", confirmedCount);
        model.addAttribute("cancelledCount", cancelledCount);

        return "user/dashboard";
    }
}
