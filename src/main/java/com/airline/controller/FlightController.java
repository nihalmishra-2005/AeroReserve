package com.airline.controller;

import com.airline.model.*;
import com.airline.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;
    private final SeatService seatService;
    private final BookingService bookingService;
    private final UserService userService;

    @GetMapping("/search")
    public String searchPage() {
        return "flight/search";
    }

    @PostMapping("/search")
    public String searchResults(@RequestParam String from,
            @RequestParam String to,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Model model) {

       List<Flight> flights = flightService.searchFlights(from, to);

        model.addAttribute("flights", flights);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("date", date);

        return "flight/results";
    }

    @GetMapping("/{id}/seats")
    public String seatSelection(@PathVariable Long id,
            @RequestParam(defaultValue = "Economy") String classType,
            Model model) {
        Flight flight = flightService.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
        Seat.ClassType cls = Seat.ClassType.valueOf(classType);
        List<Seat> seats = seatService.getSeatsByFlightAndClass(id, cls);
        model.addAttribute("flight", flight);
        model.addAttribute("seats", seats);
        model.addAttribute("selectedClass", classType);
        model.addAttribute("price", flightService.calculatePrice(flight.getBasePrice(), cls));
        return "flight/seat-selection";
    }

    @GetMapping("/{flightId}/book/{seatId}")
    public String bookingForm(@PathVariable Long flightId,
            @PathVariable Long seatId,
            Authentication auth, Model model) {
        Flight flight = flightService.findById(flightId).orElseThrow();
        Seat seat = seatService.findById(seatId).orElseThrow();
        User user = userService.findByEmail(auth.getName()).orElseThrow();
        model.addAttribute("flight", flight);
        model.addAttribute("seat", seat);
        model.addAttribute("user", user);
        return "booking/booking-form";
    }

    @PostMapping("/{flightId}/book/{seatId}")
    public String confirmBooking(@PathVariable Long flightId,
            @PathVariable Long seatId,
            @RequestParam String passengerName,
            @RequestParam String passengerEmail,
            @RequestParam String passengerPhone,
            Authentication auth, Model model) {
        try {
            Flight flight = flightService.findById(flightId).orElseThrow();
            Seat seat = seatService.findById(seatId).orElseThrow();
            User user = userService.findByEmail(auth.getName()).orElseThrow();

            if (seat.getIsBooked()) {
                model.addAttribute("error", "This seat is already booked. Please select another.");
                return "redirect:/flights/" + flightId + "/seats";
            }

            Booking booking = bookingService.createBooking(user, flight, seat,
                    passengerName, passengerEmail, passengerPhone);
            return "redirect:/bookings/" + booking.getId() + "/confirmation";
        } catch (Exception e) {
            return "redirect:/dashboard?error=true";
        }
    }
}
