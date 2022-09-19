package ru.hogwarts.school.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class FacultyNotFoundException extends RuntimeException{

    private Logger logger = LoggerFactory.getLogger(FacultyNotFoundException.class);

    public FacultyNotFoundException(String message) {
        super(message);
        logger.error(message);
    }
}
