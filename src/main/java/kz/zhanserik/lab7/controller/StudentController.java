package kz.zhanserik.lab7.controller;

import kz.zhanserik.lab7.dto.StudentDto;
import kz.zhanserik.lab7.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // --- Ескі CRUD ---
    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto studentDto) {
        StudentDto createdStudent = studentService.createStudent(studentDto);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {
        StudentDto student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        List<StudentDto> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable Long id, @RequestBody StudentDto studentDto) {
        StudentDto updatedStudent = studentService.updateStudent(id, studentDto);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<StudentDto> addCourseToStudent(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {

        StudentDto updatedStudent = studentService.addCourseToStudent(studentId, courseId);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<StudentDto> removeCourseFromStudent(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {

        StudentDto updatedStudent = studentService.removeCourseFromStudent(studentId, courseId);
        return ResponseEntity.ok(updatedStudent);
    }
}