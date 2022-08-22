package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private static long idCounter = 0;
    private final Map<Long, Faculty> facultyMap = new HashMap<>();

    public Faculty createFaculty(Faculty faculty){
        long id = ++idCounter;
        faculty.setId(id);
        return facultyMap.put(id, faculty);
    }

    public Faculty readFaculty(long id){
        return facultyMap.get(id);
    }

    public Map<Long, Faculty> readAllFaculties(){
        return facultyMap;
    }

    public Faculty updateFaculty(Faculty faculty){
        long id = faculty.getId();
        if(facultyMap.containsKey(id)){
            return facultyMap.put(id, faculty);
        } else {
            throw new FacultyNotFoundException("Faculty with such id was not found in the database");
        }
    }

    public Faculty deleteFaculty(long id){
        return facultyMap.remove(id);
    }

    public List<Faculty> filterByColor(String color) {
        return facultyMap.values().stream()
                .filter(f -> f.getColor().equals(color))
                .collect(Collectors.toList());
    }
}
