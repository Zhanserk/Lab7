package kz.zhanserik.lab7.service.impl;

import kz.zhanserik.lab7.dto.CourseDto;
import kz.zhanserik.lab7.entity.Course;
import kz.zhanserik.lab7.mapper.CourseMapper;
import kz.zhanserik.lab7.repository.CourseRepository;
import kz.zhanserik.lab7.service.CourseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseMapper courseMapper; // MapStruct-ты қолданамыз

    @Override
    public List<CourseDto> findAll() {
        return courseRepository.findAll().stream()
                .map(courseMapper::toDto) // MapStruct
                .collect(Collectors.toList());
    }

    @Override
    public CourseDto findById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));
        return courseMapper.toDto(course); // MapStruct
    }

    @Override
    public CourseDto save(CourseDto courseDto) {
        Course course = courseMapper.toEntity(courseDto); // MapStruct
        Course savedCourse = courseRepository.save(course);
        return courseMapper.toDto(savedCourse); // MapStruct
    }

    @Override
    public CourseDto update(Long id, CourseDto courseDto) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));

        existingCourse.setTitle(courseDto.getTitle());
        Course updatedCourse = courseRepository.save(existingCourse);

        return courseMapper.toDto(updatedCourse);
    }

    @Override
    public void deleteById(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new EntityNotFoundException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }
}