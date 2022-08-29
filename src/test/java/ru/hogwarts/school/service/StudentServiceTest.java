package ru.hogwarts.school.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private List<Student> dataBaseMock;
    private List<Student> expectedStudentMap;
    private Student student1;
    private Student student2;

    @BeforeEach
    public void variablesInit(){
        expectedStudentMap = new ArrayList<>();
        student1 = new Student("Harry Potter", 11);
        student1.setId(1L);
        expectedStudentMap.add(student1);
        student2 = new Student("Hermione Granger", 11);
        student2.setId(2L);
        expectedStudentMap.add(student2);
        dataBaseMock = new ArrayList<>(List.of(student1, student2));
    }

    @Test
    public void createStudentTest(){
        when(studentRepository.findAll()).thenReturn(dataBaseMock);
        when(studentRepository.save(student1)).thenReturn(dataBaseMock.get(0));
        when(studentRepository.save(student2)).thenReturn(dataBaseMock.get(1));
        Assertions.assertEquals(expectedStudentMap.get(0), studentService.createStudent(student1));
        Assertions.assertEquals(expectedStudentMap.get(1), studentService.createStudent(student2));
        Assertions.assertEquals(expectedStudentMap, studentService.readAllStudents());
    }

    @Test
    public void readStudentTest(){
        when(studentRepository.findById(1L)).thenReturn(Optional.ofNullable(dataBaseMock.get(0)));
        when(studentRepository.findById(2L)).thenReturn(Optional.ofNullable(dataBaseMock.get(1)));
        Student result1 = studentService.readStudent(1).get();
        Student result2 = studentService.readStudent(2).get();
        Assertions.assertEquals(student1, result1);
        Assertions.assertEquals(student2, result2);
    }

    @Test
    public void updateStudentTest(){
        Student updatedStudent1 = new Student("Harry Potter", 12);
        Student result;
        updatedStudent1.setId(1L);
        dataBaseMock.get(0).setAge(12);
        when(studentRepository.save(updatedStudent1)).thenReturn(dataBaseMock.get(0));
        studentService.updateStudent(updatedStudent1);
        when(studentRepository.findById(1L)).thenReturn(Optional.ofNullable(dataBaseMock.get(0)));
        if(studentService.readStudent(1).isEmpty()){
            return;
        } else {
            result = studentService.readStudent(1).get();
        }
        Assertions.assertEquals(updatedStudent1, result);
        dataBaseMock.get(0).setAge(11);
    }

    @Test
    public void deleteStudentTest(){
        dataBaseMock.remove(1);
        when(studentRepository.findAll()).thenReturn(dataBaseMock);
        studentService.deleteStudent(2);
        expectedStudentMap.remove(1);
        Assertions.assertEquals(expectedStudentMap, studentService.readAllStudents());
        expectedStudentMap.add(student2);
        dataBaseMock.add(student2);
    }

    @Test
    public void filterByAgeTest(){
        List<Student> expectedList = new ArrayList<>();
        expectedList.add(student1);
        expectedList.add(student2);
        when(studentRepository.findByAge(11)).thenReturn(dataBaseMock);
        Assertions.assertEquals(expectedList, studentService.filterByAge(11));
    }

}
