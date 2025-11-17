package kz.zhanserik.lab7.dto;

import lombok.Data;
import java.util.Set;

@Data
public class StudentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private double gpa;

    // Байланыстарды "таза" түрде көрсетеміз (ID және Аты)
    private Long facultyId;
    private String facultyName;

    // Байланыстарды "таза" түрде (тек ID) көрсетеміз
    private Set<Long> courseIds;
}