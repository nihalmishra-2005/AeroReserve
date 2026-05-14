package com.airline.controller;

import com.airline.model.*;
import com.airline.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final FlightService flightService;
    private final BookingService bookingService;
    private final UserService userService;
    private final SeatService seatService;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("totalFlights", flightService.countFlights());
        model.addAttribute("totalUsers", userService.countUsers());
        model.addAttribute("totalBookings", bookingService.countConfirmed());
        model.addAttribute("totalRevenue", bookingService.totalRevenue());
        model.addAttribute("recentBookings", bookingService.getAllBookings().stream().limit(10).toList());
        model.addAttribute("flights", flightService.getAllFlights().stream().limit(5).toList());
        return "admin/dashboard";
    }

    // ---- FLIGHTS ----
    @GetMapping("/flights")
    public String manageFlights(Model model) {
        model.addAttribute("flights", flightService.getAllFlights());
        return "admin/flights";
    }

    @GetMapping("/flights/new")
    public String newFlightForm(Model model) {
        model.addAttribute("flight", new Flight());
        return "admin/flight-form";
    }

    @GetMapping("/flights/{id}/edit")
    public String editFlightForm(@PathVariable Long id, Model model) {
        model.addAttribute("flight", flightService.findById(id).orElseThrow());
        return "admin/flight-form";
    }

    @PostMapping("/flights/save")
    public String saveFlight(@ModelAttribute Flight flight, RedirectAttributes ra) {
        try {
            if (flight.getId() == null) flightService.saveFlight(flight);
            else flightService.updateFlight(flight);
            ra.addFlashAttribute("success", "Flight saved successfully!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error saving flight: " + e.getMessage());
        }
        return "redirect:/admin/flights";
    }

    @PostMapping("/flights/{id}/delete")
    public String deleteFlight(@PathVariable Long id, RedirectAttributes ra) {
        flightService.deleteFlight(id);
        ra.addFlashAttribute("success", "Flight deleted.");
        return "redirect:/admin/flights";
    }

    // ---- BOOKINGS ----
    @GetMapping("/bookings")
    public String allBookings(Model model) {
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "admin/bookings";
    }

    @PostMapping("/bookings/{id}/cancel")
    public String cancelBooking(@PathVariable Long id, RedirectAttributes ra) {
        bookingService.cancelBooking(id);
        ra.addFlashAttribute("success", "Booking cancelled.");
        return "redirect:/admin/bookings";
    }

    // ---- USERS ----
    @GetMapping("/users")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "admin/users";
    }

    // ---- SEATS ----
    @GetMapping("/flights/{id}/seats")
    public String manageSeats(@PathVariable Long id, Model model) {
        model.addAttribute("flight", flightService.findById(id).orElseThrow());
        model.addAttribute("seats", seatService.getSeatsByFlight(id));
        return "admin/seats";
    }
}
