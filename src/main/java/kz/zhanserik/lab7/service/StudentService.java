package kz.zhanserik.lab7.service;

import kz.zhanserik.lab7.dto.StudentDto;
import java.util.List;

public interface StudentService {
    // Ескі CRUD
    StudentDto createStudent(StudentDto studentDto);
    StudentDto getStudentById(Long id);
    List<StudentDto> getAllStudents();
    StudentDto updateStudent(Long id, StudentDto studentDto);
    void deleteStudent(Long id);

    // Many-to-Many басқару (ЖАҢА)
    StudentDto addCourseToStudent(Long studentId, Long courseId);
    StudentDto removeCourseFromStudent(Long studentId, Long courseId);
}