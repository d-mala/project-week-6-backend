package davide.project_week_6_backend.bookings;

import davide.project_week_6_backend.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Booking save(@RequestBody @Validated BookingRequestDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining("; "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return bookingService.save(body);
    }

    @GetMapping
    public Page<Booking> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String sortBy) {
        return bookingService.findAll(page, size, sortBy);
    }

    @GetMapping("/{bookingId}")
    public Booking findById(@PathVariable long bookingId) {
        return bookingService.findById(bookingId);
    }

    @PutMapping("/{bookingId}")
    public Booking update(@PathVariable long bookingId, @RequestBody @Validated BookingRequestDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining("; "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return bookingService.update(bookingId, body);
    }

    @DeleteMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long bookingId) {
        bookingService.delete(bookingId);
    }
}
