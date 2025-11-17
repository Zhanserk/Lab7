package kz.zhanserik.lab7.mapper;

import kz.zhanserik.lab7.dto.CourseDto;
import kz.zhanserik.lab7.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseDto toDto(Course course);

    // DTO-дан Entity-ге айналдырғанда, студенттер тізімін елемеу
    @Mapping(target = "students", ignore = true)
    Course toEntity(CourseDto courseDto);
}