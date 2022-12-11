package com.wikimedia.person_api.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wikimedia.person_api.config.HttpClientConfig;
import com.wikimedia.person_api.domain.PersonResponse;
import com.wikimedia.person_api.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@Slf4j
public class FetchPersonImpl implements FetchPerson{
    @Value("${wikipedia.server.url}")
    private String wikipediaUrl;


    private static final ObjectMapper objectMapper =
            new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    @Override
    public String fetchPersonFromWiki(String username) throws IOException, InterruptedException {
        StringBuilder wikiUrl = new StringBuilder();
        wikiUrl.append(wikipediaUrl);
        wikiUrl.append("action=query&prop=revisions&");
        wikiUrl.append("titles=");
        wikiUrl.append(formatUserName(username));
        wikiUrl.append("&rvlimit=1&formatversion=2&format=json&rvprop=content");

        log.info("Wikipedia url: {}", wikiUrl);
        var client = HttpClient.newHttpClient();

        var request = HttpRequest.newBuilder()
                .uri(URI.create(wikiUrl.toString()))
                .timeout(Duration.ofSeconds(HttpClientConfig.CONNECTION_TIMEOUT_DURATION))
                .header("accept", "application/json")
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() / 100 != 2) {
            log.info("Failed fetch person.");
        } else {
            log.info("Completed fetching person");
        }

        log.info("RESULT: {}", response.body());

        PersonResponse personResponse = objectMapper.readValue(response.body(), PersonResponse.class);
        log.info("person response: {}", response.body());

        var res = PersonResponse.builder()
                .query(personResponse.getQuery())
                .build();

        var revision = res.getQuery().getPages().get(0).getRevisions().get(0).getContent().getDescription();

        if(StringUtils.containsIgnoreCase(revision,"short description")){
            log.info("Person Revision: {}", revision);
            String result = StringUtils.substringBetween(revision, "{{", "}}");

            log.info("RESULT : {}",result);

            var description = StringUtils.substringAfter(result, "|");
            log.info("DESCRIPTION : {}",description);
            log.info("Check for short description: {}",StringUtils.contains(revision,"short description"));

            return description;

        }
            return "Person record not found";

    }

    private String formatUserName(String username) {

        var lowerCaseUsername = username.toLowerCase();

        UnaryOperator<String> capitalize = str ->
                str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();

        String result =
                Stream.of(
                                lowerCaseUsername.split(" ")
                        ).map(capitalize)
                        .collect(
                                Collectors.joining(" ")
                        );
        return result.replaceAll(" ", "_");
    }
}
