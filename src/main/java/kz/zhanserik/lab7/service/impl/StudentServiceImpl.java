package kz.zhanserik.lab7.service.impl;

import kz.zhanserik.lab7.dto.StudentDto;
import kz.zhanserik.lab7.entity.Course;
import kz.zhanserik.lab7.entity.Faculty;
import kz.zhanserik.lab7.entity.Student;
import kz.zhanserik.lab7.mapper.StudentMapper; // MapStruct-ты импорттаймыз
import kz.zhanserik.lab7.repository.CourseRepository;
import kz.zhanserik.lab7.repository.FacultyRepository;
import kz.zhanserik.lab7.repository.StudentRepository;
import kz.zhanserik.lab7.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyRepository facultyRepository; // Faculty-мен жұмыс істеу үшін

    @Autowired
    private CourseRepository courseRepository; // Course-пен жұмыс істеу үшін

    @Autowired
    private StudentMapper studentMapper; // Lab 7-дегі toDto/toEntity орнына

    @Override
    public StudentDto createStudent(StudentDto studentDto) {
        // 1. DTO-ны Entity-ге айналдыру (MapStruct)
        Student student = studentMapper.toEntity(studentDto);

        // 2. Егер DTO-да facultyId келсе, сол Faculty-ді тауып, студентке қосу
        if (studentDto.getFacultyId() != null) {
            Faculty faculty = facultyRepository.findById(studentDto.getFacultyId())
                    .orElseThrow(() -> new EntityNotFoundException("Faculty not found with id: " + studentDto.getFacultyId()));
            student.setFaculty(faculty);
        }

        Student savedStudent = studentRepository.save(student);

        // 3. Нәтижені DTO-ға (MapStruct) айналдырып қайтару
        return studentMapper.toDto(savedStudent);
    }

    @Override
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));
        return studentMapper.toDto(student); // MapStruct
    }

    @Override
    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(studentMapper::toDto) // MapStruct
                .collect(Collectors.toList());
    }

    @Override
    public StudentDto updateStudent(Long id, StudentDto studentDto) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));

        // Негізгі өрістерді жаңарту
        existingStudent.setFirstName(studentDto.getFirstName());
        existingStudent.setLastName(studentDto.getLastName());
        existingStudent.setEmail(studentDto.getEmail());
        existingStudent.setGpa(studentDto.getGpa());

        // Faculty-ді жаңарту
        if (studentDto.getFacultyId() != null) {
            Faculty faculty = facultyRepository.findById(studentDto.getFacultyId())
                    .orElseThrow(() -> new EntityNotFoundException("Faculty not found with id: " + studentDto.getFacultyId()));
            existingStudent.setFaculty(faculty);
        } else {
            existingStudent.setFaculty(null); // Егер facultyId=null келсе, факультеттен шығару
        }

        Student updatedStudent = studentRepository.save(existingStudent);
        return studentMapper.toDto(updatedStudent); // MapStruct
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }

    // --- Many-to-Many методтары (ЖАҢА) ---

    @Override
    public StudentDto addCourseToStudent(Long studentId, Long courseId) {
        // 1. Студентті табу
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId));

        // 2. Курсты табу
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));

        // 3. Студенттің курстар тізіміне осы курсты қосу
        student.getCourses().add(course);
        Student updatedStudent = studentRepository.save(student); // Сактау

        return studentMapper.toDto(updatedStudent);
    }

    @Override
    public StudentDto removeCourseFromStudent(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));

        // 3. Студенттің курстар тізімінен осы курсты алып тастау
        student.getCourses().remove(course);
        Student updatedStudent = studentRepository.save(student);

        return studentMapper.toDto(updatedStudent);
    }
}