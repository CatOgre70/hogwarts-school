package ru.hogwarts.school.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@Profile("test")
@ExtendWith(MockitoExtension.class)
public class FacultyServiceTest {

    @Mock
    private FacultyRepository facultyRepository;

    @InjectMocks
    private FacultyService facultyService;

    private List<Faculty> expectedFacultyMap;
    private Faculty faculty1, faculty2;
    private List<Faculty> dataBaseMock;

    @BeforeEach
    public void variablesInit(){
        faculty1 = new Faculty("Gryffindor", "Red");
        faculty1.setId(1L);
        faculty2 = new Faculty("Slytherin", "Green");
        faculty2.setId(2L);
        expectedFacultyMap = new ArrayList<>(List.of(faculty1, faculty2));
        dataBaseMock = new ArrayList<>(List.of(faculty1, faculty2));
    }

    @Test
    public void createFacultyTest(){
        when(facultyRepository.findAll()).thenReturn(dataBaseMock);
        when(facultyRepository.save(faculty1)).thenReturn(dataBaseMock.get(0));
        when(facultyRepository.save(faculty2)).thenReturn(dataBaseMock.get(1));
        Assertions.assertEquals(expectedFacultyMap.get(0), facultyService.createFaculty(faculty1));
        Assertions.assertEquals(expectedFacultyMap.get(1), facultyService.createFaculty(faculty2));
        Assertions.assertEquals(expectedFacultyMap, facultyService.readAllFaculties());
    }

    @Test
    public void readFacultyTest(){
        when(facultyRepository.findById(1L)).thenReturn(Optional.ofNullable(dataBaseMock.get(0)));
        when(facultyRepository.findById(2L)).thenReturn(Optional.ofNullable(dataBaseMock.get(1)));
        Optional<Faculty> result1 = facultyService.readFaculty(1L);
        Optional<Faculty> result2 = facultyService.readFaculty(2L);
        Assertions.assertEquals(faculty1, result1.get());
        Assertions.assertEquals(faculty2, result2.get());
    }

    @Test
    public void updateFacultyTest(){
        Faculty updatedFaculty = new Faculty("Gryffindor", "Scarlet");
        updatedFaculty.setId(1L);
        dataBaseMock.get(0).setColor("Scarlet");
        when(facultyRepository.save(updatedFaculty)).thenReturn(dataBaseMock.get(0));
        when(facultyRepository.findById(1L)).thenReturn(Optional.ofNullable(dataBaseMock.get(0)));
        facultyService.updateFaculty(updatedFaculty);
        Assertions.assertEquals(updatedFaculty, facultyService.readFaculty(1).get());
        dataBaseMock.get(0).setColor("Red");
    }

    @Test
    public void deleteFacultyTest(){
        expectedFacultyMap.remove(1);
        dataBaseMock.remove(1);
        when(facultyRepository.findAll()).thenReturn(dataBaseMock);
        facultyService.deleteFaculty(2);
        Assertions.assertEquals(expectedFacultyMap, facultyService.readAllFaculties());
        expectedFacultyMap.add(faculty2);
        dataBaseMock.add(faculty2);
    }

    @Test
    public void filterByColorTest(){
        when(facultyRepository.findByColorIgnoreCase("Red")).thenReturn(List.of(faculty1));
        List<Faculty> expectedList = new ArrayList<>(List.of(faculty1));
        Assertions.assertEquals(expectedList, facultyService.filterByColor("Red"));
    }

}
