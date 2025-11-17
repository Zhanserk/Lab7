package kz.zhanserik.lab7.service;

import kz.zhanserik.lab7.dto.CourseDto;
import java.util.List;

public interface CourseService {
    List<CourseDto> findAll();
    CourseDto findById(Long id);
    CourseDto save(CourseDto courseDto);
    CourseDto update(Long id, CourseDto courseDto);
    void deleteById(Long id);
}