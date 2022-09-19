package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.net.URI;
import java.util.List;
import java.util.Set;

@Profile("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
        Student student = givenStudentWith("Miles Bletchley", 16, "Slytherin", "Green");
        ResponseEntity<Student> response = saveStudentInDatabase(getURIBuilder().build().toUri(), student);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
    }

    @Test
    public void getStudentTest() {
        Student student = givenStudentWith("Miles Bletchley", 16, "Slytherin", "Green");
        ResponseEntity<Student> response = saveStudentInDatabase(getURIBuilder().build().toUri(), student);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();

        Student createdStudent = response.getBody();

        URI uri = getURIBuilder().path("/{id}").buildAndExpand(createdStudent.getId()).toUri();
        response = restTemplate.getForEntity(uri, Student.class);
        Assertions.assertThat(response.getBody()).isEqualTo(createdStudent);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getAllStudentsTest() {
        Student student18 = givenStudentWith("studentName1", 18, "Slytherin", "Green");
        Student student25 = givenStudentWith("studentName2", 25, "Slytherin", "Green");
        Student student28 = givenStudentWith("studentName3", 28, "Slytherin", "Green");
        Student student32 = givenStudentWith("studentName3", 32, "Slytherin", "Green");
        URI uri = getURIBuilder().build().toUri();
        saveStudentInDatabase(uri, student18);
        saveStudentInDatabase(uri, student25);
        saveStudentInDatabase(uri, student28);
        saveStudentInDatabase(uri, student32);

        ResponseEntity<Set<Student>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Set<Student>>() {
                });
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Set<Student> actualResult = response.getBody();
        resetIds(actualResult);
        Assertions.assertThat(actualResult).containsExactlyInAnyOrder(student18, student25, student28, student32);
    }

    @Test
    public void getAllStudentsByAgeTest() {
        Student student18 = givenStudentWith("studentName1", 18, "Slytherin", "Green");
        Student student25 = givenStudentWith("studentName2", 25, "Slytherin", "Green");
        Student student28 = givenStudentWith("studentName3", 28, "Slytherin", "Green");
        Student student32 = givenStudentWith("studentName3", 32, "Slytherin", "Green");
        URI uri = getURIBuilder().build().toUri();
        saveStudentInDatabase(uri, student18);
        saveStudentInDatabase(uri, student25);
        saveStudentInDatabase(uri, student28);
        saveStudentInDatabase(uri, student32);

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("age", "25");
        studentsAreFoundByCriteria(queryParams, student25);
    }

    @Test
    public void getAllStudentsByAgeBetweenTest() {
        Student student18 = givenStudentWith("studentName1", 18, "Slytherin", "Green");
        Student student25 = givenStudentWith("studentName2", 25, "Slytherin", "Green");
        Student student28 = givenStudentWith("studentName3", 28, "Slytherin", "Green");
        Student student32 = givenStudentWith("studentName3", 32, "Slytherin", "Green");
        URI uri = getURIBuilder().build().toUri();
        saveStudentInDatabase(uri, student18);
        saveStudentInDatabase(uri, student25);
        saveStudentInDatabase(uri, student28);
        saveStudentInDatabase(uri, student32);

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("min", "20");
        queryParams.add("max", "30");
        studentsAreFoundByCriteria(queryParams, student25, student28);
    }

    @Test
    public void getStudentFacultyTest() {
        Student student = givenStudentWith("studentName1", 18, "Slytherin", "Green");
        student = saveStudentInDatabase(getURIBuilder().build().toUri(), student).getBody();
        Faculty faculty = student.getFaculty();
        URI uri = getURIBuilder().path("/{id}/faculty").buildAndExpand(student.getId()).toUri();
        Assertions
                .assertThat(this.restTemplate.getForObject(uri, Faculty.class)).isEqualTo(faculty);
    }

    @Test
    public void updateStudentTest() {
        Student student = givenStudentWith("Miles Bletchley", 16, "Slytherin", "Green");
        ResponseEntity<Student> response = saveStudentInDatabase(getURIBuilder().build().toUri(), student);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        student = response.getBody();
        updatingStudent(student, "Miles Bletchley", 17);
        studentHasBeenUpdated(student, "Miles Bletchley", 17);
    }

    @Test
    public void deleteStudentTest() {
        Student student = givenStudentWith("Miles Bletchley", 16, "Slytherin", "Green");
        ResponseEntity<Student> response = saveStudentInDatabase(getURIBuilder().build().toUri(), student);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        student = response.getBody();

        URI uri = getURIBuilder().cloneBuilder().path("/{id}").buildAndExpand(student.getId()).toUri();
        restTemplate.delete(uri);
        response = restTemplate.getForEntity(uri, Student.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private void studentHasBeenUpdated(Student student, String newName, int newAge) {
        URI uri = getURIBuilder().path("/{id}").buildAndExpand(student.getId()).toUri();
        ResponseEntity<Student> response = restTemplate.getForEntity(uri, Student.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getAge()).isEqualTo(newAge);
        Assertions.assertThat(response.getBody().getName()).isEqualTo(newName);
    }

    private void updatingStudent(Student student, String newName, int newAge) {
        student.setName(newName);
        student.setAge(newAge);

        restTemplate.put(getURIBuilder().build().toUri(), student);
    }

    private Student givenStudentWith(String name, Integer age, String facultyName, String facultyColor) {
        Student student = new Student(name, age);
        Faculty faculty = new Faculty(facultyName, facultyColor);
        Faculty createdFaculty;
        List<Faculty> facultyList = facultyService.findByColorOrName(facultyName, facultyColor);
        if(facultyList.isEmpty() || facultyList == null) {
            createdFaculty = facultyService.createFaculty(faculty);
        } else {
            createdFaculty = facultyList.get(0);
        }
        student.setFaculty(createdFaculty);
        return student;
    }

    private ResponseEntity<Student> saveStudentInDatabase(URI uri, Student student) {
        return this.restTemplate.postForEntity(uri, student, Student.class);
    }

    private void studentsAreFoundByCriteria(MultiValueMap<String, String> queryParams, Student... students) {
        URI uri = getURIBuilder().queryParams(queryParams).build().toUri();
        ResponseEntity<Set<Student>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Set<Student>>() {
                });
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Set<Student> actualResult = response.getBody();
        resetIds(actualResult);
        Assertions.assertThat(actualResult).containsExactlyInAnyOrder(students);

    }

    private void resetIds(Set<Student> students) {
        students.forEach(it -> it.setId(null));
    }

    private UriComponentsBuilder getURIBuilder() {
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("/student");
    }

}
