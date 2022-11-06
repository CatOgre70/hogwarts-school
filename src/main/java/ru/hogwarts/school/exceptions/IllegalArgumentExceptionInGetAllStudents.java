package ru.hogwarts.school.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class IllegalArgumentExceptionInGetAllStudents extends IllegalArgumentException{

    private Logger logger = LoggerFactory.getLogger(IllegalArgumentExceptionInGetAllStudents.class);

    public IllegalArgumentExceptionInGetAllStudents(String s) {
        super(s);
        logger.error(s);
    }
}
