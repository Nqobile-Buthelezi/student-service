package za.co.pass.studentservice.service;

import org.springframework.stereotype.Service;
import za.co.pass.studentservice.dto.StudentRequestDTO;
import za.co.pass.studentservice.dto.StudentResponseDTO;
import za.co.pass.studentservice.exception.EmailAlreadyExistsException;
import za.co.pass.studentservice.exception.StudentNotFoundException;
import za.co.pass.studentservice.mapper.StudentMapper;
import za.co.pass.studentservice.model.Student;
import za.co.pass.studentservice.repository.StudentRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

// For the business logic and where the DTO conversion will happen per request4
@Service
public class StudentService {

    // The student repository is responsible for interacting with the database
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        // This design pattern is called dependency injection
        // Instead of instantiating the repository we are injecting it via the constructor
        // This makes our code more modular and easier to test
        // Because we can add mock student repositories to test it out,
        // and it adheres to the principles of inversion control
        this.studentRepository = studentRepository;
    }

    // Returns all students in our database
    // Our service layer will convert the domain entity
    // models into a response DTO that has specific properties
    public List<StudentResponseDTO> getStudents() {
        List<Student> students = studentRepository.findAll();
        // We need to convert the Student Domain Entity Model (DEM),
        // into a Response Data Transfer Object (DTO)
        // We will create a helper class that accepts a DEM and returns a DTO.
        return students.stream().map(StudentMapper::toDTO).toList();
    }

    public StudentResponseDTO createStudent(StudentRequestDTO studentRequestDTO) {
        // if the email is not unique and does exist within the database we throw an error
        if (studentRepository.existsByEmail(studentRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("A student with this email address: " + studentRequestDTO.getEmail() + " already exists.");
        }
        // Must convert our student request DTO into a DEM or Student Entity Model object
        Student studentDomainEntityModel = StudentMapper.toModel(studentRequestDTO);
        // The save method is what will persist the Domain entity model to our database
        // without writing much code, as the functionality is built in
        Student newStudent = studentRepository.save(studentDomainEntityModel);

        return StudentMapper.toDTO(newStudent);
    }

    public StudentResponseDTO updateStudent(UUID id, StudentRequestDTO studentRequestDTO) {
        // Check if the student exists in the database
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(
                        "Student with id: "
                                + id + " not found"
                        )
                );

        if (studentRepository.existsByEmailAndIdNot(studentRequestDTO.getEmail(), id)) {
            // If the email already exists for another student, throw an exception
            // checking if the email exists for another student with a different id
            throw new EmailAlreadyExistsException(
                    "A student with this email address: " +
                            studentRequestDTO.getEmail() + " already exists."
            );
        }

        // Update the student with the new information
        student.setName(studentRequestDTO.getName());
        student.setAddress(studentRequestDTO.getAddress());
        student.setEmail(studentRequestDTO.getEmail());
        student.setDateOfBirth(LocalDate.parse(studentRequestDTO.getDateOfBirth()));

        Student updatedStudent = studentRepository.save(student);

        return StudentMapper.toDTO(updatedStudent);
    }

    public void deleteStudent(UUID id) {
        // Check if the student exists in the database
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(
                        "Student with id: "
                                + id + " not found"
                        )
                );
        // If the student exists, delete it from the database
        studentRepository.delete(student);
    }

    public StudentResponseDTO getStudent(UUID id) {
        // Check if the student exists in the database
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(
                        "Student with id: "
                                + id + " not found"
                        )
                );
        // Convert the student to a DTO and return it
        return StudentMapper.toDTO(student);
    }
}
