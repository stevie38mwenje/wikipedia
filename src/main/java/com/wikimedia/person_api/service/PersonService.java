package com.wikimedia.person_api.service;

import java.io.IOException;

public interface PersonService {
    String getPersonDescription(String username) throws IOException, InterruptedException;
}
