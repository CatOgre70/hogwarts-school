package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public List<Student> getAllStudents(){
        return studentService.readAllStudents();
    }

    @GetMapping("/age")
    public List<Student> filterByAge(@RequestParam("age") int age){
        return studentService.filterByAge(age);
    }

    @GetMapping("/findbyagebetween")
    public List<Student> findByAgeBetween(@RequestParam("min") int min, @RequestParam("max") int max){
        return studentService.findByAgeBetween(min, max);
    }

    @GetMapping("/getstudentfacultybyid")
    public Faculty getStudentFaculty(@RequestParam("id") int id){
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
