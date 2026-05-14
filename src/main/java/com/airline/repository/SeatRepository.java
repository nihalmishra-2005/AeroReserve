package com.airline.repository;

import com.airline.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByFlightIdOrderBySeatNumberAsc(Long flightId);
    List<Seat> findByFlightIdAndClassTypeOrderBySeatNumberAsc(Long flightId, Seat.ClassType classType);
    long countByFlightIdAndClassTypeAndIsBooked(Long flightId, Seat.ClassType classType, Boolean isBooked);
    boolean existsByFlightId(Long flightId);
}
