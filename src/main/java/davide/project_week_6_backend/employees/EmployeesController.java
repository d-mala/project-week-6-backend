package davide.project_week_6_backend.employees;

import davide.project_week_6_backend.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    @Autowired
    private EmployeesService employeesService;

    @GetMapping
    public Page<Employee> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String sortBy) {
        return this.employeesService.findAll(page, size, sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee save(@RequestBody @Validated EmployeeRequestDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining("; "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }

        return this.employeesService.save(body);
    }

    @GetMapping("/{employeeId}")
    public Employee findById(@PathVariable long employeeId) {
        return this.employeesService.findById(employeeId);
    }


    @PutMapping("/{employeeId}")
    public Employee findByIdAndUpdate(@PathVariable long employeeId, @RequestBody @Validated EmployeeRequestDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload!");
        }
        return this.employeesService.findByIdAndUpdate(employeeId, body);
    }


    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable long employeeId) {
        this.employeesService.findByIdAndDelete(employeeId);
    }

    @PatchMapping("/{employeeId}/avatar")
    public Employee uploadAvatar(@PathVariable long employeeId, @RequestParam("avatar") MultipartFile file) {
        return this.employeesService.uploadAvatar(employeeId, file);
    }
}
