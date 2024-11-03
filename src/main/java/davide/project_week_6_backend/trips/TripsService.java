package davide.project_week_6_backend.trips;

import davide.project_week_6_backend.exceptions.BadRequestException;
import davide.project_week_6_backend.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TripsService {

    @Autowired
    private TripsRepository tripsRepository;

    public Trip save(TripRequestDTO body) {
        Trip newTrip = new Trip(body.destination(), body.date(), body.status());

        return this.tripsRepository.save(newTrip);
    }

    public Page<Trip> findAll(int page, int size, String sortBy) {
        if (size > 100)
            size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.tripsRepository.findAll(pageable);
    }

    public Trip findById(long id) {
        return this.tripsRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Trip findByIdAndUpdate(long id, TripRequestDTO body) {
        Trip found = this.findById(id);

        if (!found.getDestination().equals(body.destination())) {
            this.tripsRepository.findByDestination(body.destination()).ifPresent(
                    Trip -> {
                        throw new BadRequestException("Destinazione " + body.destination() + " già in uso");
                    }
            );
        }

        found.setDestination(body.destination());
        found.setDate(body.date());
        found.setStatus(body.status());
        return this.tripsRepository.save(found);
    }

    public void findByIdAndDelete(long id) {
        Trip found = this.findById(id);
        this.tripsRepository.delete(found);
    }

}
