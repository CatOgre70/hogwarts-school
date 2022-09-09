package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createStudentTest() {
        Student student = new Student("Miles Bletchley", 11);
        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student",
                        student, String.class)).isNotNull();
    }

    @Test
    public void getStudentTest() {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/9",
                        String.class)).isNotNull();
    }

    @Test
    public void getAllStudentsTest() {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student",
                        String.class)).isNotNull();
    }

    @Test
    public void getStudentFacultyTest() {
        Faculty faculty = facultyService.readFaculty(2).orElse(null);
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/9/faculty",
                        Faculty.class)).isEqualTo(faculty);
    }

    @Test
    public void updateStudentTest() throws URISyntaxException {
        Student student = new Student("Miles Bletchley", 11);
        student.setId(9L);
        Faculty faculty = new Faculty("Slytherin", "Green");
        faculty.setId(2L);
        student.setFaculty(faculty);

        String baseUrl = "http://localhost:" + port + "/student";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true"); // What does it mean, "X-COM-PERSIST"?

        HttpEntity<Student> request = new HttpEntity<>(student, headers);

        ResponseEntity<Student> result = this.restTemplate.postForEntity(uri, request, Student.class);

        org.junit.jupiter.api.Assertions.assertEquals(200, result.getStatusCodeValue());
    }

}
