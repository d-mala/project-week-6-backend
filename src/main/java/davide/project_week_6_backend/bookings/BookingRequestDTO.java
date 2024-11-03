package davide.project_week_6_backend.bookings;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record BookingRequestDTO(
        @NotBlank(message = "La data della richiesta è obbligatoria")
        LocalDate requestDate,

        @Size(max = 500, message = "Le note devono contenere al massimo 500 caratteri")
        String notes,

        @NotBlank(message = "L'ID del dipendente è obbligatorio")
        Long employeeId,

        @NotBlank(message = "L'ID del viaggio è obbligatorio")
        Long tripId
) {
}
