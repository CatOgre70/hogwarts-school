package ru.hogwarts.school.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class FacultyNotFoundException extends RuntimeException{

    public FacultyNotFoundException(String exceptionDescription){
        super(exceptionDescription);
    }

}
