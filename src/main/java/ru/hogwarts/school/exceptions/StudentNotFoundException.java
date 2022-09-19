package ru.hogwarts.school.exceptions;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class StudentNotFoundException extends RuntimeException{

    private Logger logger = LoggerFactory.getLogger(StudentNotFoundException.class);
    public StudentNotFoundException(String message) {
        super(message);
        logger.error(message);
    }
}
