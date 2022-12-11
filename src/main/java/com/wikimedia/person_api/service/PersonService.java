package com.wikimedia.person_api.service;

import com.wikimedia.person_api.domain.GenericResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface PersonService {
    ResponseEntity<GenericResponse> getPersonDescription(String username) throws IOException, InterruptedException;
}
