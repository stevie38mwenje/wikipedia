package com.wikimedia.person_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;
import java.time.Duration;

@Configuration
public class HttpClientConfig {
    public static final long CONNECTION_TIMEOUT_DURATION = 5;

    @Bean
    public HttpClient httpClient() {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                //.followRedirects(Redirect.SAME_PROTOCOL)
                //.proxy(ProxySelector.of(new InetSocketAddress("www-proxy.com", 8080)))
                //.authenticator(Authenticator.getDefault())
                .connectTimeout(Duration.ofSeconds(CONNECTION_TIMEOUT_DURATION))
                //.executor(new SimpleAsyncTaskExecutor())
                .build();

        return client;
    }


    @Bean
    public RestTemplate restTesmplate() {
        return new RestTemplate();
    }

}
