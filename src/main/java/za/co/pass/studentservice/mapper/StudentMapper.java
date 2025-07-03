package za.co.pass.studentservice.mapper;

import za.co.pass.studentservice.dto.StudentRequestDTO;
import za.co.pass.studentservice.dto.StudentResponseDTO;
import za.co.pass.studentservice.model.Student;

import java.time.LocalDate;

public class StudentMapper {

    private StudentMapper() {}

    public static StudentResponseDTO toDTO(Student student) {
        // Instantiate a new DTO
        StudentResponseDTO studentDTO = new StudentResponseDTO();
        // Set each field you require to be passed into the front end
        studentDTO.setId(student.getId().toString());
        studentDTO.setName(student.getName());
        studentDTO.setAddress(student.getAddress());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setDateOfBirth(student.getDateOfBirth().toString());

        return studentDTO;
    }

    public static Student toModel(StudentRequestDTO studentRequestDTO) {
        Student newStudent = new Student();

        newStudent.setName(studentRequestDTO.getName());
        newStudent.setAddress(studentRequestDTO.getAddress());
        newStudent.setEmail(studentRequestDTO.getEmail());
        newStudent.setDateOfBirth(LocalDate.parse(studentRequestDTO.getDateOfBirth()));
        newStudent.setRegisteredDate(LocalDate.parse(studentRequestDTO.getRegisteredDate()));

        return newStudent;
    }

}
