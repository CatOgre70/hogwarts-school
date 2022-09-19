package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public Faculty createFaculty(Faculty faculty){
        logger.info("Method \"FacultyService.createFaculty()\" was invoked");
        return facultyRepository.save(faculty);
    }

    public Optional<Faculty> readFaculty(long id){
        logger.info("Method \"FacultyService.readFaculty()\" was invoked");
        return facultyRepository.findById(id);
    }

    public List<Faculty> readAllFaculties(){
        logger.info("Method \"FacultyService.readAllFaculties()\" was invoked");
        return facultyRepository.findAll();
    }

    public Faculty updateFaculty(Faculty faculty){
        logger.info("Method \"FacultyService.updateFaculty()\" was invoked");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id){
        logger.info("Method \"FacultyService.deleteFaculty()\" was invoked");
        facultyRepository.deleteById(id);
    }

    public List<Faculty> filterByColor(String color) {
        logger.info("Method \"FacultyService.filterByColor()\" was invoked");
        return facultyRepository.findByColorIgnoreCase(color);
    }

    public List<Faculty> findByColorOrName(String color, String name) {
        logger.info("Method \"FacultyService.findByColorOrName()\" was invoked");
        return facultyRepository.findByColorContainingIgnoreCaseOrNameContainingIgnoreCase(color, name);
    }

    public Set<Student> getFacultyAllStudents(Long id) {
        logger.info("Method \"FacultyService.getFacultyAllStudents()\" was invoked");
        Optional<Faculty> faculty = readFaculty(id);
        if(faculty.isPresent()){
            return faculty.get().getStudents();
        } else {
            throw new FacultyNotFoundException("Faculty with id = " + id + " is not found in the database");
        }
    }
}
