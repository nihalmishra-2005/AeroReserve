package com.airline.service;

import com.airline.model.Flight;
import com.airline.model.Seat;
import com.airline.repository.FlightRepository;
import com.airline.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private final SeatRepository seatRepository;

    // Price multipliers per class
    public static final BigDecimal ECONOMY_MULTIPLIER = BigDecimal.valueOf(1.0);
    public static final BigDecimal BUSINESS_MULTIPLIER = BigDecimal.valueOf(1.75);
    public static final BigDecimal FIRST_MULTIPLIER = BigDecimal.valueOf(2.8);

    public List<Flight> searchFlights(String source, String destination) {
        return flightRepository.searchFlights(source, destination);
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAllByOrderByDepartureTimeAsc();
    }

    public Optional<Flight> findById(Long id) {
        return flightRepository.findById(id);
    }

    @Transactional
    public Flight saveFlight(Flight flight) {
        Flight saved = flightRepository.save(flight);
        // Generate seats if new flight
        if (!seatRepository.existsByFlightId(saved.getId())) {
            generateSeats(saved);
        }
        return saved;
    }

    @Transactional
    public Flight updateFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }

    public long countFlights() {
        return flightRepository.count();
    }

    // Generate seats for a newly created flight
    private void generateSeats(Flight flight) {
        List<Seat> seats = new ArrayList<>();

        // Economy: rows 10-19, seats A-F
        generateClassSeats(seats, flight, 10, flight.getTotalEconomySeats(),
                Seat.ClassType.Economy, flight.getBasePrice().multiply(ECONOMY_MULTIPLIER));

        // Business: rows 5-9, seats A-D
        generateClassSeats(seats, flight, 5, flight.getTotalBusinessSeats(),
                Seat.ClassType.Business, flight.getBasePrice().multiply(BUSINESS_MULTIPLIER));

        // First: rows 1-4, seats A-D
        generateClassSeats(seats, flight, 1, flight.getTotalFirstSeats(),
                Seat.ClassType.First, flight.getBasePrice().multiply(FIRST_MULTIPLIER));

        seatRepository.saveAll(seats);
    }

    private void generateClassSeats(List<Seat> seats, Flight flight,
            int startRow, int total,
            Seat.ClassType classType, BigDecimal price) {
        String[] cols = (classType == Seat.ClassType.Economy)
                ? new String[] { "A", "B", "C", "D", "E", "F" }
                : new String[] { "A", "B", "C", "D" };
        int count = 0;
        int row = startRow;
        while (count < total) {
            for (String col : cols) {
                if (count >= total)
                    break;
                seats.add(Seat.builder()
                        .flight(flight)
                        .seatNumber(row + col)
                        .classType(classType)
                        .isBooked(false)
                        .price(price)
                        .build());
                count++;
            }
            row++;
        }
    }

    public BigDecimal calculatePrice(BigDecimal basePrice, Seat.ClassType classType) {
        return switch (classType) {
            case Economy -> basePrice.multiply(ECONOMY_MULTIPLIER);
            case Business -> basePrice.multiply(BUSINESS_MULTIPLIER);
            case First -> basePrice.multiply(FIRST_MULTIPLIER);
        };
    }
}
