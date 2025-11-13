package kz.zhanserik.lab7.service.impl;

import kz.zhanserik.lab7.dto.StudentDto;
import kz.zhanserik.lab7.entity.Student;
import kz.zhanserik.lab7.repository.StudentRepository;
import kz.zhanserik.lab7.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    private StudentDto toDto(Student student) {
        return new StudentDto(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getGpa()
        );
    }

    private Student toEntity(StudentDto studentDto) {
        Student student = new Student();
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setEmail(studentDto.getEmail());
        student.setGpa(studentDto.getGpa());
        return student;
    }

    @Override
    public StudentDto createStudent(StudentDto studentDto) {
        Student student = toEntity(studentDto);
        Student savedStudent = studentRepository.save(student);
        return toDto(savedStudent);
    }

    @Override
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));
        return toDto(student);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> studentEntities = studentRepository.findAll();
        List<StudentDto> studentDtos = new ArrayList<>();

        for (Student student : studentEntities) {
            studentDtos.add(toDto(student));
        }

        return studentDtos;
    }

    @Override
    public StudentDto updateStudent(Long id, StudentDto studentDto) {

        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));

        existingStudent.setFirstName(studentDto.getFirstName());
        existingStudent.setLastName(studentDto.getLastName());
        existingStudent.setEmail(studentDto.getEmail());
        existingStudent.setGpa(studentDto.getGpa());

        Student updatedStudent = studentRepository.save(existingStudent);
        return toDto(updatedStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }
}