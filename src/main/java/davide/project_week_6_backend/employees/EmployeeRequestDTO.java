package davide.project_week_6_backend.employees;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmployeeRequestDTO(
        @NotBlank(message = "Lo username è obbligatorio")
        @Size(min = 3, max = 50, message = "Lo username deve contenere al massimo 50 caratteri")
        String username,

        @NotBlank(message = "Il nome è obbligatorio")
        @Size(min = 3, max = 50, message = "Il nome deve contenere al massimo 50 caratteri")
        String firstName,

        @NotBlank(message = "Il cognome è obbligatorio")
        @Size(min = 3, max = 50, message = "Il cognome deve contenere al massimo 50 caratteri")
        String lastName,

        @NotBlank(message = "L'email è obbligatoria")
        @Email(message = "L'email deve essere valida")
        @Size(max = 100, message = "L'email deve contenere al massimo 100 caratteri")
        String email
) {
}
