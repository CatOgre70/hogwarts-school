package ru.hogwarts.school.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student){
        return studentRepository.save(student);
    }

    public Optional<Student> readStudent(long id){
        return studentRepository.findById(id);
    }

    public List<Student> readAllStudents() {
        return studentRepository.findAll();
    }


    public Student updateStudent(Student student){
        return studentRepository.save(student);
    }

    public void deleteStudent(long id){
        studentRepository.deleteById(id);
    }

    public List<Student> filterByAge(int age) {
        return studentRepository.findByAge(age);
    }

    public List<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getFaculty(int id) {
        Optional<Student> student = readStudent(id);
        if (student.isPresent()) {
            return student.get().getFaculty();
        } else {
            throw new StudentNotFoundException("Student with such id is not found");
        }
    }

    public int getTotalNumberOfStudentsInTheSchool() {
        return studentRepository.getTotalNumberOfStudentsInTheSchool();
    }

    public double getAverageAge() {
        return studentRepository.getAverageAge();
    }

    public List<Student> findLastFiveStudents() {
        int total = studentRepository.getTotalNumberOfStudentsInTheSchool();
        int count = 0;
        if(total < 5) {
            count = total;
        } else {
            count = 5;
        }
        List<Student> students = studentRepository.sortStudentsById();
        List<Student> result = new ArrayList<>(count);
        for(int i = 0; i < count; i++)
            result.add(students.get(i));
        return result;
    }


}
