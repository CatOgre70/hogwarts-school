package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {

        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty){
        Faculty createdFaculty = facultyService.createFaculty(faculty);
        return ResponseEntity.ok(createdFaculty);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id){
        Optional<Faculty> faculty = facultyService.readFaculty(id);
        if(faculty.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(faculty.get());
        }
    }

    @GetMapping
    public List<Faculty> getAllFaculties(){
        return facultyService.readAllFaculties();
    }

    @GetMapping("/color")
    public List<Faculty> filterByColor(@RequestParam("color") String color){
        return facultyService.filterByColor(color);
    }

    @GetMapping("/getfacultyallstudents")
    public Set<Student> getFacultyAllStudents(@RequestParam("id") int id){
        return facultyService.getFacultyAllStudents(id);
    }

    @PutMapping
    public Faculty updateFaculty(@RequestBody Faculty faculty){
        return facultyService.updateFaculty(faculty);
    }

    @DeleteMapping("/{id}")
    public void deleteFaculty(@PathVariable Long id){
        facultyService.deleteFaculty(id);
    }

    @GetMapping("/findbystring")
    public List<Faculty> findByColorOrName(@RequestParam("string") String str){
        return facultyService.findByColorOrName(str, str);
    }

}
