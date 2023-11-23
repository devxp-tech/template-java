package com.devxptech.templatejava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloWorldController {

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> helloWorld() {

        return new ResponseEntity<>("Hello world for your app template-java", HttpStatus.OK);
    }

    @GetMapping(path = "/{param}")
    public ResponseEntity<?> helloWorldParam(@PathVariable Integer param) {

        if (param == 1) {
            return new ResponseEntity<>("Hello world for your app template-java", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Diferente de 1 ", HttpStatus.BAD_REQUEST);
        }

    }

}
