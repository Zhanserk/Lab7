package kz.zhanserik.lab7.mapper;

import kz.zhanserik.lab7.dto.FacultyDto;
import kz.zhanserik.lab7.entity.Faculty;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") // Spring-ке бұл маппер екенін айтады
public interface FacultyMapper {
    FacultyDto toDto(Faculty faculty);
    Faculty toEntity(FacultyDto facultyDto);
}