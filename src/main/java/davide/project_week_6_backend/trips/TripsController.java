package davide.project_week_6_backend.trips;

import davide.project_week_6_backend.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/trips")
public class TripsController {

    @Autowired
    private TripsService tripsService;

    @GetMapping
    public Page<Trip> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "id") String sortBy) {
        return this.tripsService.findAll(page, size, sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Trip save(@RequestBody @Validated TripRequestDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining("; "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }

        return this.tripsService.save(body);
    }

    @GetMapping("/{id}")
    public Trip findById(@PathVariable long id) {
        return this.tripsService.findById(id);
    }


    @PutMapping("/{id}")
    public Trip findByIdAndUpdate(@PathVariable long id, @RequestBody @Validated TripRequestDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload!");
        }
        return this.tripsService.findByIdAndUpdate(id, body);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable long id) {
        this.tripsService.findByIdAndDelete(id);
    }
}