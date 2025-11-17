package kz.zhanserik.lab7.service;

import kz.zhanserik.lab7.dto.FacultyDto;
import java.util.List;

public interface FacultyService {
    List<FacultyDto> findAll();
    FacultyDto findById(Long id);
    FacultyDto save(FacultyDto facultyDto);
    FacultyDto update(Long id, FacultyDto facultyDto);
    void deleteById(Long id);
}