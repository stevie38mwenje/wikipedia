package com.wikimedia.person_api.util;

import java.io.IOException;

public interface FetchPerson {
    String fetchPersonFromWiki(String username) throws IOException, InterruptedException;
}
