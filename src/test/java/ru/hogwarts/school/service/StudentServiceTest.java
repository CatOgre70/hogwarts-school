package ru.hogwarts.school.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.Map;

public class StudentServiceTest {

    private final StudentService studentService = new StudentService();
    private Map<Long, Student> expectedStudentMap;
    private Student student1;
    private Student student2;

    @BeforeEach
    public void variablesInit(){
        expectedStudentMap = new HashMap<>();
        student1 = new Student("Harry Potter", 11);
        student1.setId(1);
        expectedStudentMap.put((long) 1, student1);
        student2 = new Student("Hermione Granger", 11);
        student2.setId(2);
        expectedStudentMap.put((long) 2, student2);
        studentService.createStudent(student1);
        studentService.createStudent(student2);
    }

    @Test
    public void createStudentTest(){
        Assertions.assertEquals(expectedStudentMap, studentService.readAllStudents());
    }

    @Test
    public void readStudentTest(){
        Student result1 = studentService.readStudent(1);
        Student result2 = studentService.readStudent(2);
        Assertions.assertEquals(student1, result1);
        Assertions.assertEquals(student2, result2);
    }

    @Test
    public void updateStudentTest(){
        Student updatedStudent1 = new Student("Harry Potter", 12);
        updatedStudent1.setId(1);
        studentService.updateStudent(updatedStudent1);
        Assertions.assertEquals(studentService.readStudent(1), updatedStudent1);
    }

    @Test
    public void deleteStudentTest(){
        studentService.deleteStudent(2);
        expectedStudentMap.remove((long) 2);
        Assertions.assertEquals(expectedStudentMap, studentService.readAllStudents());
    }

}
