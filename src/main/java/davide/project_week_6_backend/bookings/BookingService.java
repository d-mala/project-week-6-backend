package davide.project_week_6_backend.bookings;

import davide.project_week_6_backend.employees.Employee;
import davide.project_week_6_backend.employees.EmployeesRepository;
import davide.project_week_6_backend.exceptions.BadRequestException;
import davide.project_week_6_backend.exceptions.NotFoundException;
import davide.project_week_6_backend.trips.Trip;
import davide.project_week_6_backend.trips.TripsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private TripsRepository tripsRepository;

    public Booking save(BookingRequestDTO body) {
        // Verifica che il dipendente esista
        Employee employee = employeesRepository.findById(body.employeeId())
                .orElseThrow(() -> new NotFoundException(body.employeeId()));

        // Verifica che il viaggio esista
        Trip trip = tripsRepository.findById(body.tripId())
                .orElseThrow(() -> new NotFoundException(body.tripId()));

        // Controlla se il dipendente ha già una prenotazione per la stessa data
        if (!bookingRepository.findByEmployeeIdAndRequestDate(employee.getId(), body.requestDate()).isEmpty()) {
            throw new BadRequestException("Il dipendente ha già una prenotazione per il " + body.requestDate());
        }

        Booking booking = new Booking(body.requestDate(), body.notes(), employee, trip);
        return bookingRepository.save(booking);
    }


    public Page<Booking> findAll(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.bookingRepository.findAll(pageable);
    }


    public Booking findById(long bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException(bookingId));
    }

    public Booking update(long bookingId, BookingRequestDTO body) {
        Booking booking = findById(bookingId);

        // Aggiorna il booking, ma prima verifica se ci sono conflitti di data
        if (!bookingRepository.findByEmployeeIdAndRequestDate(booking.getEmployee().getId(), body.requestDate()).isEmpty()) {
            throw new BadRequestException("Il dipendente ha già una prenotazione per il " + body.requestDate());
        }

        booking.setRequestDate(body.requestDate());
        booking.setNotes(body.notes());

        return bookingRepository.save(booking);
    }

    public void delete(long bookingId) {
        Booking booking = findById(bookingId);
        bookingRepository.delete(booking);
    }
}
