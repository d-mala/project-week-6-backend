package davide.project_week_6_backend.trips;

import davide.project_week_6_backend.exceptions.BadRequestException;
import davide.project_week_6_backend.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class TripsService {

    @Autowired
    private TripsRepository tripsRepository;

    public Trip save(TripRequestDTO body) {
        // Conversione della stringa status in enum
        Status status;
        try {
            status = Status.valueOf(body.status().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Lo stato fornito non è valido. Valori accettati: " + Arrays.toString(Status.values()));
        }

        Trip newTrip = new Trip(body.destination(), body.date(), status);
        return tripsRepository.save(newTrip);
    }


    public Page<Trip> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return tripsRepository.findAll(pageable);
    }

    public Trip findById(long tripId) {
        return tripsRepository.findById(tripId).orElseThrow(() -> new NotFoundException(tripId));
    }

    public Trip findByIdAndUpdate(long tripId, TripRequestDTO body) {
        Trip found = findById(tripId);
        found.setDestination(body.destination());
        found.setDate(body.date());
        found.setStatus(Status.valueOf(body.status()));
        return tripsRepository.save(found);
    }

    public void findByIdAndDelete(long tripId) {
        Trip found = findById(tripId);
        tripsRepository.delete(found);
    }

    public Trip updateStatus(Long tripId, Status status) {
        Trip trip = tripsRepository.findById(tripId)
                .orElseThrow(() -> new NotFoundException(tripId));

        if (status == null) {
            throw new BadRequestException("Lo stato fornito non è valido. Valori accettati: " + Status.values());
        }

        trip.setStatus(status);
        return tripsRepository.save(trip);
    }
}
