package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FacultyController.class)
public class FacultyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyService facultyService;

    @InjectMocks
    private FacultyController facultyController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createFacultyTest() throws Exception {
        Faculty faculty = new Faculty("Slytherin", "Green");
        faculty.setId(2L);

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", "Slytherin");
        facultyObject.put("color", "Green");
        facultyObject.put("id", 2L);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/faculty")
                .content(facultyObject.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("Slytherin"))
                .andExpect(jsonPath("$.color").value("Green"));
    }

    @Test
    public void getFacultyTest() throws Exception{
        Faculty faculty = new Faculty("Slytherin", "Green");
        faculty.setId(2L);

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/faculty/{id}", 2L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("Slytherin"))
                .andExpect(jsonPath("$.color").value("Green"));
    }

    @Test
    public void getAllFacultiesTest() throws Exception {

        Faculty faculty1 = new Faculty("Gryffindor", "Scarlet");
        faculty1.setId(1L);
        Faculty faculty2 = new Faculty("Slytherin", "Green");
        faculty2.setId(2L);

        when(facultyService.readAllFaculties()).thenReturn(List.of(faculty1, faculty2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(faculty1, faculty2))));
    }

    @Test
    public void getAllFacultiesByColorTest() throws Exception {

        Faculty faculty1 = new Faculty("Gryffindor", "Green");
        faculty1.setId(1L);
        Faculty faculty2 = new Faculty("Slytherin", "Green");
        faculty2.setId(2L);

        when(facultyRepository.findByColorIgnoreCase(any(String.class))).thenReturn(List.of(faculty1, faculty2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .queryParam("color", "green")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(faculty1, faculty2))));
    }

    @Test
    public void getAllFacultiesByNameTest() throws Exception {

        Faculty faculty1 = new Faculty("Gryffindor", "Green");
        faculty1.setId(1L);
        Faculty faculty2 = new Faculty("Slytherin", "Green");
        faculty2.setId(2L);

        when(facultyRepository.findByColorContainingIgnoreCaseOrNameContainingIgnoreCase(any(String.class),
                any(String.class)))
                .thenReturn(List.of(faculty1, faculty2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .queryParam("findbystring", "y")
                        .queryParam("color", "green")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(faculty1, faculty2))));
    }

    @Test
    public void updateFacultyTest() throws Exception {
        Faculty faculty = new Faculty("Gryffindor", "Scarlet");
        faculty.setId(1L);
        Faculty updatedFaculty = new Faculty("Slytherin", "Green");
        updatedFaculty.setId(1L);

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", "Slytherin");
        facultyObject.put("color", "Green");
        facultyObject.put("id", 1L);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(updatedFaculty);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/faculty")
                .content(facultyObject.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Slytherin"))
                .andExpect(jsonPath("$.color").value("Green"));

    }

    @Test
    public void deleteFacultyTest() throws Exception {
        Faculty faculty = new Faculty("Gryffindor", "Scarlet");
        faculty.setId(1L);

        when(facultyService.readFaculty(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/faculty/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(facultyRepository, atLeastOnce()).deleteById(1L);
    }



}
