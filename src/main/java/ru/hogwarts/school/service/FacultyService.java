package ru.hogwarts.school.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    FacultyService(FacultyRepository facultyRepository){
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty){
        return facultyRepository.save(faculty);
    }

    public ResponseEntity<Faculty> readFaculty(long id){
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if(faculty.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty.get());
    }

    public List<Faculty> readAllFaculties(){
        return facultyRepository.findAll();
    }

    public Faculty updateFaculty(Faculty faculty){
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id){
        facultyRepository.deleteById(id);
    }

    public List<Faculty> filterByColor(String color) {
        return facultyRepository.findByColor(color);
    }
}
