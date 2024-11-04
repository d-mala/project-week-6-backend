package davide.project_week_6_backend.bookings;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record BookingRequestDTO(
        @NotNull(message = "La data della richiesta è obbligatoria")
        LocalDate requestDate,

        @Size(max = 500, message = "Le note devono contenere al massimo 500 caratteri")
        String notes,

        @NotNull(message = "L'ID del dipendente è obbligatorio")
        Long employeeId,

        @NotNull(message = "L'ID del viaggio è obbligatorio")
        Long tripId
) {
}
