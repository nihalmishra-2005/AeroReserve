package com.airline.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "flights")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Flight {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Column(name = "flight_number", unique = true, nullable = false)
    private String flightNumber;

    @NotBlank @Column(name = "airline_name", nullable = false)
    private String airlineName;

    @NotBlank @Column(name = "source_city", nullable = false)
    private String sourceCity;

    @NotBlank @Column(name = "source_code", nullable = false)
    private String sourceCode;

    @NotBlank @Column(name = "destination_city", nullable = false)
    private String destinationCity;

    @NotBlank @Column(name = "destination_code", nullable = false)
    private String destinationCode;

    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @DecimalMin("0.0") @Column(name = "base_price", nullable = false)
    private BigDecimal basePrice;

    @Enumerated(EnumType.STRING)
    private FlightStatus status = FlightStatus.SCHEDULED;

    @Column(name = "total_economy_seats")
    private Integer totalEconomySeats = 60;

    @Column(name = "total_business_seats")
    private Integer totalBusinessSeats = 20;

    @Column(name = "total_first_seats")
    private Integer totalFirstSeats = 10;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Seat> seats;

    @OneToMany(mappedBy = "flight", fetch = FetchType.LAZY)
    private List<Booking> bookings;

    public enum FlightStatus { SCHEDULED, DELAYED, CANCELLED, COMPLETED }

    public String getDurationFormatted() {
        if (durationMinutes == null) return "N/A";
        int h = durationMinutes / 60, m = durationMinutes % 60;
        return h + "h " + m + "m";
    }
}
