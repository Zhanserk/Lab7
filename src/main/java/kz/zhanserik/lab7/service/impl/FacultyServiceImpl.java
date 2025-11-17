package kz.zhanserik.lab7.service.impl;

import kz.zhanserik.lab7.dto.FacultyDto;
import kz.zhanserik.lab7.entity.Faculty;
import kz.zhanserik.lab7.mapper.FacultyMapper;
import kz.zhanserik.lab7.repository.FacultyRepository;
import kz.zhanserik.lab7.service.FacultyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private FacultyMapper facultyMapper; // MapStruct-ты қолданамыз

    @Override
    public List<FacultyDto> findAll() {
        return facultyRepository.findAll().stream()
                .map(facultyMapper::toDto) // MapStruct
                .collect(Collectors.toList());
    }

    @Override
    public FacultyDto findById(Long id) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Faculty not found with id: " + id));
        return facultyMapper.toDto(faculty); // MapStruct
    }

    @Override
    public FacultyDto save(FacultyDto facultyDto) {
        Faculty faculty = facultyMapper.toEntity(facultyDto); // MapStruct
        Faculty savedFaculty = facultyRepository.save(faculty);
        return facultyMapper.toDto(savedFaculty); // MapStruct
    }

    @Override
    public FacultyDto update(Long id, FacultyDto facultyDto) {
        Faculty existingFaculty = facultyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Faculty not found with id: " + id));

        existingFaculty.setName(facultyDto.getName());
        Faculty updatedFaculty = facultyRepository.save(existingFaculty);

        return facultyMapper.toDto(updatedFaculty);
    }

    @Override
    public void deleteById(Long id) {
        if (!facultyRepository.existsById(id)) {
            throw new EntityNotFoundException("Faculty not found with id: " + id);
        }
        facultyRepository.deleteById(id);
    }
}