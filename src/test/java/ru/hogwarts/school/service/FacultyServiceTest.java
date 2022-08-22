package ru.hogwarts.school.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacultyServiceTest {

    FacultyService facultyService = new FacultyService();
    Map<Long, Faculty> expectedFacultyMap;
    Faculty faculty1, faculty2;

    @BeforeEach
    public void variablesInit(){
        expectedFacultyMap = new HashMap<>();
        faculty1 = new Faculty("Gryffindor", "Red");
        faculty1.setId(1);
        expectedFacultyMap.put((long) 1, faculty1);
        faculty2 = new Faculty("Slytherin", "Green");
        faculty2.setId(2);
        expectedFacultyMap.put((long) 2, faculty2);
        facultyService.createFaculty(faculty1);
        facultyService.createFaculty(faculty2);
    }

    @Test
    public void createFacultyTest(){
        Assertions.assertEquals(expectedFacultyMap, facultyService.readAllFaculties());
    }

    @Test
    public void readFacultyTest(){
        Faculty result1 = facultyService.readFaculty(1);
        Faculty result2 = facultyService.readFaculty(2);
        Assertions.assertEquals(faculty1, result1);
        Assertions.assertEquals(faculty2, result2);
    }

    @Test
    public void updateFacultyTest(){
        Faculty updatedFaculty = new Faculty("Gryffindor", "Scarlet");
        updatedFaculty.setId(1);
        facultyService.updateFaculty(updatedFaculty);
        Assertions.assertEquals(updatedFaculty, facultyService.readFaculty(1));
    }

    @Test
    public void deleteFacultyTest(){
        facultyService.deleteFaculty(2);
        expectedFacultyMap.remove((long) 2);
        Assertions.assertEquals(expectedFacultyMap, facultyService.readAllFaculties());
    }

    @Test
    public void filterByAgeTest(){
        List<Faculty> expectedList = new ArrayList<>();
        expectedList.add(faculty1);
        Assertions.assertEquals(expectedList, facultyService.filterByColor("Red"));
    }

}
