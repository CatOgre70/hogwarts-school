package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService {

    private static long idCounter = 0;

    private final Map<Long, Student> studentMap = new HashMap<>();

    public Student createStudent(Student student){
        long id = ++idCounter;
        student.setId(id);
        return studentMap.put(id, student);
    }

    public Student readStudent(long id){
        return studentMap.get(id);
    }

    public Map<Long, Student> readAllStudents() {
        return studentMap;
    }


    public Student updateStudent(Student student){
        long id = student.getId();
        if(studentMap.containsKey(id)){
            return studentMap.put(id, student);
        } else {
            throw new StudentNotFoundException("Student with such id was not found in the database");
        }
    }

    public Student deleteStudent(long id){
        return studentMap.remove(id);
    }

}
