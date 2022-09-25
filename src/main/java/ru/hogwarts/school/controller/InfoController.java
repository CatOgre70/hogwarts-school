package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/getport")
public class InfoController {

    @Value("${server.port}")
    private Integer serverPort;

    @GetMapping()
    public ResponseEntity<Integer> getServerPort(){
        return ResponseEntity.ok(serverPort);
    }

    @GetMapping("/calculation")
    public Integer calculation(){
        Logger logger = LoggerFactory.getLogger(InfoController.class);
        logger.info("Calculation procedure started");
        int result = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, (a, b) -> a + b );
        logger.info("Calculation procedure finished");
        return result;
    }

}
