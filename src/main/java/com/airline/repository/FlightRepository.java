package com.airline.repository;

import com.airline.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("""
                SELECT f FROM Flight f
                WHERE
                (
                    LOWER(f.sourceCity) LIKE LOWER(CONCAT('%', :source, '%'))
                    AND LOWER(f.destinationCity) LIKE LOWER(CONCAT('%', :destination, '%'))
                )
                OR
                (
                    LOWER(f.sourceCity) LIKE LOWER(CONCAT('%', :destination, '%'))
                    AND LOWER(f.destinationCity) LIKE LOWER(CONCAT('%', :source, '%'))
                )
                AND f.status = 'SCHEDULED'
            """)
    List<Flight> searchFlights(
            @Param("source") String source,
            @Param("destination") String destination);

    List<Flight> findByStatusOrderByDepartureTimeAsc(Flight.FlightStatus status);

    List<Flight> findAllByOrderByDepartureTimeAsc();
}