package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.exceptions.IllegalArgumentExceptionInGetAllStudents;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        Student createdStudent = studentService.createStudent(student);
        return ResponseEntity.ok(createdStudent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id){
        Optional<Student> student = studentService.readStudent(id);
        if(student.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student.get());
    }

    @GetMapping
    public List<Student> getAllStudents(@RequestParam(value = "age", required = false) Integer age,
                                        @RequestParam(value = "min", required = false) Integer min,
                                        @RequestParam(value = "max", required = false) Integer max){
        if(age == null && min == null && max == null){
            return studentService.readAllStudents();
        } else if(age != null && min == null && max == null){
            return studentService.filterByAge(age);
        } else if(age == null && min != null && max != null){
            return studentService.findByAgeBetween(min, max);
        } else {
            throw new IllegalArgumentExceptionInGetAllStudents("Wrong parameters in the REST request");
        }
    }

    @GetMapping("/statistics/totalpopulation")
    public int getTotalNumberOfStudentsInTheSchool(){
        return studentService.getTotalNumberOfStudentsInTheSchool();
    }

    @GetMapping("/statistics/averageage")
    public double getAverageAge(){
        return studentService.getAverageAge();
    }

    @GetMapping("/getlastfivestudents")
    List<Student> getLastFiveStudents(){
        return studentService.findLastFiveStudents();
    }

    @GetMapping("/{id}/faculty")
    public Faculty getStudentFaculty(@PathVariable int id){
        return studentService.getFaculty(id);
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student){
        Student student1 = studentService.updateStudent(student);
        return ResponseEntity.ok(student1);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
    }

}
