package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public Student createStudent(Student student){
        logger.info("Method \"StudentService.createStudent()\" was invoked");
        return studentRepository.save(student);
    }

    public Optional<Student> readStudent(long id){
        logger.info("Method \"StudentService.readStudent()\" was invoked");
        return studentRepository.findById(id);
    }

    public List<Student> readAllStudents() {
        logger.info("Method \"StudentService.readAllStudents()\" was invoked");
        return studentRepository.findAll();
    }


    public Student updateStudent(Student student){
        logger.info("Method \"StudentService.updateStudent()\" was invoked");
        return studentRepository.save(student);
    }

    public void deleteStudent(long id){
        logger.info("Method \"StudentService.deleteStudent()\" was invoked");
        studentRepository.deleteById(id);
    }

    public List<Student> filterByAge(int age) {
        logger.info("Method \"StudentService.filterByAge()\" was invoked");
        return studentRepository.findByAge(age);
    }

    public List<Student> findByAgeBetween(int min, int max) {
        logger.info("Method \"StudentService.findByAgeBetween()\" was invoked");
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getFaculty(int id) {
        logger.info("Method \"StudentService.getFaculty()\" was invoked");
        Optional<Student> student = readStudent(id);
        if (student.isPresent()) {
            return student.get().getFaculty();
        } else {
            throw new StudentNotFoundException("Student with id = " + id + " is not found");
        }
    }

    public int getTotalNumberOfStudentsInTheSchool() {
        logger.info("Method \"StudentService.getTotalNumberOfStudentsInTheSchool()\" was invoked");
        return studentRepository.getTotalNumberOfStudentsInTheSchool();
    }

    public double getAverageAge() {
        logger.info("Method \"StudentService.getAverageAge()\" was invoked");
        return studentRepository.getAverageAge();
    }

    public List<Student> findLastFiveStudents() {
        logger.info("Method \"StudentService.findLastFiveStudents()\" was invoked");
        return studentRepository.findLastFiveStudents();
    }

}
