package ru.hogwarts.school.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByAge(int age);

    List<Student> findByAgeBetween(int min, int max);

    Student findStudentById(Long id);

    @Query(value = "select count(*) from student", nativeQuery = true)
    int getTotalNumberOfStudentsInTheSchool();

    @Query(value = "select avg(age) from student", nativeQuery = true)
    double getAverageAge();

    @Query(value = "select * from student order by id desc limit 5", nativeQuery = true)
    List<Student> findLastFiveStudents();

}
