package com.airline.service;

import com.airline.model.Seat;
import com.airline.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;

    public List<Seat> getSeatsByFlight(Long flightId) {
        return seatRepository.findByFlightIdOrderBySeatNumberAsc(flightId);
    }

    public List<Seat> getSeatsByFlightAndClass(Long flightId, Seat.ClassType classType) {
        return seatRepository.findByFlightIdAndClassTypeOrderBySeatNumberAsc(flightId, classType);
    }

    public Optional<Seat> findById(Long id) {
        return seatRepository.findById(id);
    }

    public long countAvailable(Long flightId, Seat.ClassType classType) {
        return seatRepository.countByFlightIdAndClassTypeAndIsBooked(flightId, classType, false);
    }

    public Seat save(Seat seat) {
        return seatRepository.save(seat);
    }
}
