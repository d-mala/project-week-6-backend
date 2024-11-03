package davide.project_week_6_backend.trips;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record TripRequestDTO(
        @NotBlank(message = "La destinazione è obbligatoria")
        @Size(max = 100, message = "La destinazione deve contenere al massimo 100 caratteri")
        String destination,

        @FutureOrPresent(message = "La data deve essere oggi o nel futuro")
        LocalDate date,

        @NotBlank(message = "Lo stato è obbligatorio")
        @Pattern(regexp = "IN_PROGRESS|COMPLETED", message = "Lo stato deve essere 'in programma' o 'completato'")
        Status status
) {
}