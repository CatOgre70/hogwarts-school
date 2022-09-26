package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<Student> getAllWithNameStartedWith(char ch) {
        List<Student> sList = studentRepository.findAll();
        Comparator<Student> compareByName = Comparator.comparing(Student::getName);
        sList.forEach(s -> s.setName(s.getName().toUpperCase()));
        String str = (ch + "").toUpperCase();
        return sList.stream()
                .filter(s -> s.getName().startsWith(str))
                .sorted(compareByName)
                .collect(Collectors.toList());
    }

    public double getAverageAge1() {
        List<Student> sList = studentRepository.findAll();
        return sList.stream()
                .mapToDouble(Student::getAge)
                .average().getAsDouble();
    }

    public String allStudentsToConsoleOutput() {
        List<Student> sList = studentRepository.findAllSortedById();

        StudentService studentService1 = new StudentService(studentRepository);

        studentService1.studentConsoleOut(sList.get(0));
        studentService1.studentConsoleOut(sList.get(1));

        new Thread(() -> {
            studentService1.studentConsoleOut(sList.get(2));
            studentService1.studentConsoleOut(sList.get(3));
        }).start();
        new Thread(() -> {
            studentService1.studentConsoleOut(sList.get(4));
            studentService1.studentConsoleOut(sList.get(5));
        }).start();

        studentService1.studentConsoleOut(sList.get(6));
        studentService1.studentConsoleOut(sList.get(7));

        return "Ok";
    }

    public String allStudentsToConsoleSyncOutput() {
        List<Student> sList = studentRepository.findAllSortedById();

        StudentService studentService1 = new StudentService(studentRepository);

        Thread thread1 = new Thread(() -> {
            studentService1.studentConsoleSyncOut(sList.get(2));
            studentService1.studentConsoleSyncOut(sList.get(3));
        });
        Thread thread2 = new Thread(() -> {
            studentService1.studentConsoleSyncOut(sList.get(4));
            studentService1.studentConsoleSyncOut(sList.get(5));
        });

        studentConsoleSyncOut(sList.get(0));
        studentConsoleSyncOut(sList.get(1));
        thread1.start();
        thread2.start();
        studentService1.studentConsoleSyncOut(sList.get(6));
        studentService1.studentConsoleSyncOut(sList.get(7));

        return "Ok";
    }

    private void studentConsoleOut(Student student) {
        try {
            System.out.println(student);
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            System.out.println("Thread was interrupted");
        }
    }

    private void studentConsoleSyncOut(Student student) {
        try {
            synchronized (StudentService.class) {
                System.out.println(student);
                Thread.sleep(1000);
            }
        } catch(InterruptedException e) {
            System.out.println("Thread was interrupted");
        }
    }


}
