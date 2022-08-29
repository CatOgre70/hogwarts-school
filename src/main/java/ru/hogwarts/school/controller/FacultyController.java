package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

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

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id){
        return facultyService.readFaculty(id);
    }

    @GetMapping
    public ResponseEntity<List<Faculty>> getAllFaculties(){
        return ResponseEntity.ok(facultyService.readAllFaculties());
    }

    @GetMapping("/color")
    public ResponseEntity<List<Faculty>> filterByColor(@RequestParam("color") String color){
        List<Faculty> facultyFilteredByColor = facultyService.filterByColor(color);
        if(facultyFilteredByColor.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyFilteredByColor);
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty){
        Faculty faculty1 = facultyService.updateFaculty(faculty);
        return ResponseEntity.ok(faculty1);
    }

    @DeleteMapping("{id}")
    public void deleteFaculty(@PathVariable Long id){
        facultyService.deleteFaculty(id);
    }

}
