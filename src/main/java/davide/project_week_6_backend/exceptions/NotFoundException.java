package davide.project_week_6_backend.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id) {
        super("Il record con id " + id + " non è stato trovato!");
    }
}
