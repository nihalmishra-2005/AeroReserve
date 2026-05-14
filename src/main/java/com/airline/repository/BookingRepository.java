package com.airline.repository;

import com.airline.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserIdOrderByBookedAtDesc(Long userId);
    Optional<Booking> findByBookingRef(String bookingRef);
    List<Booking> findAllByOrderByBookedAtDesc();

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = 'CONFIRMED'")
    long countConfirmedBookings();

    @Query("SELECT SUM(b.amountPaid) FROM Booking b WHERE b.status = 'CONFIRMED'")
    Double totalRevenue();
}
