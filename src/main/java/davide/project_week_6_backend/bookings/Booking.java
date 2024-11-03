package davide.project_week_6_backend.bookings;

import davide.project_week_6_backend.employees.Employee;
import davide.project_week_6_backend.trips.Trip;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "prenotazioni")
@Getter
@Setter
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "request_date")
    private LocalDate requestDate;

    @Column(length = 500)
    private String notes;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @OneToOne
    @JoinColumn(name = "trip_id", nullable = false, unique = true)
    private Trip trip;

    // Costruttore con tutti i campi eccetto l'ID
    public Booking(LocalDate requestDate, String notes, Employee employee, Trip trip) {
        this.requestDate = requestDate;
        this.notes = notes;
        this.employee = employee;
        this.trip = trip;
    }
}

