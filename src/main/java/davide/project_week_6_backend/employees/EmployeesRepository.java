package davide.project_week_6_backend.employees;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeesRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
}
