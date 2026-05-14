package com.airline.service;

import com.airline.model.*;
import com.airline.repository.BookingRepository;
import com.airline.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;

    @Transactional
    public Booking createBooking(User user, Flight flight, Seat seat,
                                 String passengerName,
                                 String passengerEmail,
                                 String passengerPhone) {

        // Mark seat as booked
        seat.setIsBooked(true);
        seatRepository.save(seat);

        Booking booking = Booking.builder()
                .bookingRef(generateRef())
                .user(user)
                .flight(flight)
                .seat(seat)
                .passengerName(passengerName)
                .passengerEmail(passengerEmail)
                .passengerPhone(passengerPhone)
                .classType(seat.getClassType())
                .amountPaid(seat.getPrice())
                .status(Booking.BookingStatus.CONFIRMED)
                .bookedAt(LocalDateTime.now())
                .build();

        return bookingRepository.save(booking);
    }

    @Transactional
    public void cancelBooking(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(Booking.BookingStatus.CANCELLED);
        booking.setCancelledAt(LocalDateTime.now());

        // Free seat
        Seat seat = booking.getSeat();
        seat.setIsBooked(false);

        seatRepository.save(seat);
        bookingRepository.save(booking);
    }

    public List<Booking> getUserBookings(Long userId) {
        return bookingRepository.findByUserIdOrderByBookedAtDesc(userId);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAllByOrderByBookedAtDesc();
    }

    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }

    public long countConfirmed() {
        return bookingRepository.countConfirmedBookings();
    }

    public Double totalRevenue() {

        Double rev = bookingRepository.totalRevenue();

        return rev != null ? rev : 0.0;
    }

    private String generateRef() {
        return "AR" +
                UUID.randomUUID()
                        .toString()
                        .toUpperCase()
                        .replace("-", "")
                        .substring(0, 8);
    }
}