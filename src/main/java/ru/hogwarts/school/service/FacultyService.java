package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    FacultyService(FacultyRepository facultyRepository){
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty){
        return facultyRepository.save(faculty);
    }

    public Optional<Faculty> readFaculty(long id){
        return facultyRepository.findById(id);
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

    public List<Faculty> findByColorOrName(String color, String name) {
        return facultyRepository.findByColorContainingIgnoreCaseOrNameContainingIgnoreCase(color, name);
    }

    public Set<Student> getFacultyAllStudents(int id) {
        Optional<Faculty> faculty = readFaculty(id);
        if(faculty.isPresent()){
            return faculty.get().getStudents();
        } else {
            throw new FacultyNotFoundException("Faculty with such id is not found in the database");
        }
    }
}
