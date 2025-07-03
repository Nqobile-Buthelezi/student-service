package za.co.pass.studentservice.controller;

import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import za.co.pass.studentservice.dto.StudentRequestDTO;
import za.co.pass.studentservice.dto.StudentResponseDTO;
import za.co.pass.studentservice.dto.validators.CreatePatientValidationGroup;
import za.co.pass.studentservice.service.StudentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/students") // This displays that this particular controller
// will handle requests relating to the /students endpoint
// http://localhost:4000/students
public class StudentController {

    private final StudentService studentService;

    // Dependency injection design pattern to inject the student service,
    // and it's functionality into the controller
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Create methods to handle CRUD requests
    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getStudents() {
        List<StudentResponseDTO> students = studentService.getStudents();
        return ResponseEntity.ok().body(students); // Respond with status code 200,
        // with the body composing of the list of student response DTOs.
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudent(@PathVariable UUID id) {
        // Path variable is used to extract the id from the URL
        StudentResponseDTO studentResponseDTO = studentService.getStudent(id);
        return ResponseEntity.ok().body(studentResponseDTO); // Respond with status code 200,
        // with the body composing of the student response DTO.
    }

    @PostMapping
    public ResponseEntity<StudentResponseDTO> createStudent(
            @Validated({Default.class, CreatePatientValidationGroup.class}) // allows additional validation groups to be used
            @RequestBody StudentRequestDTO studentRequestDTO) {
        // Valid checks if the validation
        // we added is adhered to, Request Body annotation takes the JSON and
        // transforms it into the request DTO for the module
        StudentResponseDTO studentResponseDTO = studentService.createStudent(studentRequestDTO);
        return ResponseEntity.ok().body(studentResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(
            @PathVariable UUID id,
            @Validated(Default.class) // student can be updated without the CreatePatientValidationGroup
            @RequestBody StudentRequestDTO studentRequestDTO) {
        // Path variable is used to extract the id from the URL
        // Valid checks if the validation we added is adhered to,
        // Request Body annotation takes the JSON and transforms it into the request DTO for the module
        StudentResponseDTO studentResponseDTO = studentService.updateStudent(id, studentRequestDTO);
        return ResponseEntity.ok().body(studentResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable UUID id) {
        // Deletes the student with the given id
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build(); // Responds with status code 204, indicating no content
    }
}
