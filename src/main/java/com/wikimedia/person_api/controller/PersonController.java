package com.wikimedia.person_api.controller;

import com.wikimedia.person_api.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/person/")
@Slf4j

public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping("description")
    String getPersonDescription(@RequestParam(name = "Username", required = true) String username) throws IOException, InterruptedException {
            return personService.getPersonDescription(username);
        }
    }

