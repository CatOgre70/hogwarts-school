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
    public List<Faculty> getAllFaculties(@RequestParam(value = "color", required = false) String color,
                                         @RequestParam(value = "findbystring", required = false) String str){
        if(color == null && str == null){
            return facultyService.readAllFaculties();
        } else if(color != null && str == null){
            return facultyService.filterByColor(color);
        } else if(color == null && str != null){
            return facultyService.findByColorOrName(str, str);
        } else {
            return facultyService.findByColorOrName(color, str);
        }
    }

    @GetMapping("/{id}/students")
    public Set<Student> getFacultyAllStudents(@PathVariable Long id){
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

}
