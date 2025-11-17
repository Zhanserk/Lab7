package kz.zhanserik.lab7.mapper;

import kz.zhanserik.lab7.dto.StudentDto;
import kz.zhanserik.lab7.entity.Course;
import kz.zhanserik.lab7.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    // "student.faculty.id"-ні "studentDto.facultyId"-ге сал
    @Mapping(source = "faculty.id", target = "facultyId")
    // "student.faculty.name"-ді "studentDto.facultyName"-ге сал
    @Mapping(source = "faculty.name", target = "facultyName")
    // "student.courses"-ты "studentDto.courseIds"-қа "coursesToIds" ережесімен сал
    @Mapping(source = "courses", target = "courseIds", qualifiedByName = "coursesToIds")
    StudentDto toDto(Student student);

    // Кері айналдырғанда байланыстарды елемеу (оны Сервис өзі басқарады)
    @Mapping(target = "faculty", ignore = true)
    @Mapping(target = "courses", ignore = true)
    Student toEntity(StudentDto studentDto);

    // MapStruct-қа Set<Course> -тан Set<Long> -қа қалай өтуді үйрететін көмекші метод
    @Named("coursesToIds")
    default Set<Long> coursesToIds(Set<Course> courses) {
        if (courses == null) {
            return new HashSet<>(); // Бос Set қайтару (null емес)
        }
        return courses.stream()
                .map(Course::getId)
                .collect(Collectors.toSet());
    }
}