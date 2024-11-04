package davide.project_week_6_backend.trips;

import davide.project_week_6_backend.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/trips")
public class TripsController {

    @Autowired
    private TripsService tripsService;

    @GetMapping
    public Page<Trip> findAll(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "id") String sortBy) {
        return tripsService.findAll(page, size, sortBy);
    }

    @GetMapping("/{tripId}")
    public Trip findById(@PathVariable long tripId) {
        return tripsService.findById(tripId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Trip save(@RequestBody @Validated TripRequestDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining("; "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return tripsService.save(body);
    }


    @PutMapping("/{tripId}")
    public Trip findByIdAndUpdate(@PathVariable long tripId, @RequestBody @Validated TripRequestDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining("; "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return tripsService.findByIdAndUpdate(tripId, body);
    }

    @DeleteMapping("/{tripId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable long tripId) {
        tripsService.findByIdAndDelete(tripId);
    }

    @PatchMapping("/{tripId}/status")
    public Trip updateStatus(@PathVariable long tripId, @RequestParam String status) {
        try {
            // Converti manualmente la stringa allo status enum
            Status tripStatus = Status.valueOf(status.toUpperCase());
            return tripsService.updateStatus(tripId, tripStatus);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Lo stato fornito non è valido. Valori accettati: " + Arrays.toString(Status.values()));
        }
    }
}
