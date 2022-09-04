package ru.hogwarts.school.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class IllegalArgumentExceptionInGetAllStudents extends IllegalArgumentException{
    public IllegalArgumentExceptionInGetAllStudents(String s) {
        super(s);
    }
}
