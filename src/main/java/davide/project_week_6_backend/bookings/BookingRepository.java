package davide.project_week_6_backend.bookings;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByEmployeeIdAndRequestDate(Long employeeId, LocalDate requestDate);

    boolean existsByTripId(Long tripId);
}

