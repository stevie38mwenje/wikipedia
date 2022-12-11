package com.wikimedia.person_api.service.impl;

import com.wikimedia.person_api.service.PersonService;
import com.wikimedia.person_api.util.FetchPersonImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final FetchPersonImpl fetchPerson;

    public PersonServiceImpl(FetchPersonImpl fetchPerson) {
        this.fetchPerson = fetchPerson;
    }

    @Override
    public String getPersonDescription(String username) throws IOException, InterruptedException {
        return fetchPerson.fetchPersonFromWiki(username);
    }
}
