package davide.project_week_6_backend.employees;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import davide.project_week_6_backend.exceptions.BadRequestException;
import davide.project_week_6_backend.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class EmployeesService {

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private Cloudinary cloudinaryUploader;

    public Employee save(EmployeeRequestDTO body) {
        this.employeesRepository.findByEmail(body.email()).ifPresent(
                Employee -> {
                    throw new BadRequestException("Email " + body.email() + " già in uso!");
                }
        );

        Employee newEmployee = new Employee(body.firstName(), body.lastName(), body.email(), body.username(),
                "https://ui-avatars.com/api/?name=" + body.firstName() + "+" + body.lastName());

        return this.employeesRepository.save(newEmployee);
    }

    public Page<Employee> findAll(int page, int size, String sortBy) {
        if (size > 100)
            size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.employeesRepository.findAll(pageable);
    }

    public Employee findById(long employeeId) {
        return this.employeesRepository.findById(employeeId).orElseThrow(() -> new NotFoundException(employeeId));
    }

    public Employee findByIdAndUpdate(long employeeId, EmployeeRequestDTO body) {
        Employee found = this.findById(employeeId);

        if (!found.getEmail().equals(body.email())) {
            this.employeesRepository.findByEmail(body.email()).ifPresent(
                    Employee -> {
                        throw new BadRequestException("Email " + body.email() + " già in uso!");
                    }
            );
        }

        found.setFirstName(body.firstName());
        found.setLastName(body.lastName());
        found.setEmail(body.email());
        found.setUsername(body.username());
        found.setProfileImage("https://ui-avatars.com/api/?name=" + body.firstName() + "+" + body.lastName());

        return this.employeesRepository.save(found);
    }

    public void findByIdAndDelete(long employeeId) {
        Employee found = this.findById(employeeId);
        this.employeesRepository.delete(found);
    }

    public Employee uploadAvatar(long employeeId, MultipartFile file) {

        String url = null;
        try {
            url = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        } catch (IOException e) {
            throw new BadRequestException("Ci sono stati problemi con l'upload del file!");
        }

        Employee found = this.findById(employeeId);
        found.setProfileImage(url);

        return this.employeesRepository.save(found);


    }
}
