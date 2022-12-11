package com.wikimedia.person_api.service.impl;

import com.wikimedia.person_api.domain.GenericResponse;
import com.wikimedia.person_api.exception.CustomException;
import com.wikimedia.person_api.service.PersonService;
import com.wikimedia.person_api.util.FetchPersonImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static io.micrometer.common.util.StringUtils.isBlank;
import static java.util.Objects.isNull;

@Service
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final FetchPersonImpl fetchPerson;

    public PersonServiceImpl(FetchPersonImpl fetchPerson) {
        this.fetchPerson = fetchPerson;
    }

    @Override
    public ResponseEntity<GenericResponse> getPersonDescription(String username) throws IOException, InterruptedException {
        log.info("Short Description: {}",fetchPerson.fetchPersonFromWiki(username));

        ResponseEntity<GenericResponse> response;

        try {
            var description = fetchPerson.fetchPersonFromWiki(username);
            if (isBlank(description))
                return ResponseEntity.badRequest().body(GenericResponse.builder()
                    .description("User not found").build());
            log.info("User description fetched from wikipedia api: {}", description);

            response = ResponseEntity.ok(GenericResponse.builder()
                    .description(description).message("Successfully fetched short description").build());

        } catch (IllegalArgumentException ex) {
            response = ResponseEntity.badRequest().body(GenericResponse.builder()
                    .message("Ensure you specify the correct details.").build());

        }
        return response;
    }



}
