package davide.project_week_6_backend.trips;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TripsRepository extends JpaRepository<Trip, Long> {
    Optional<Trip> findByDestination(String destination);
}
