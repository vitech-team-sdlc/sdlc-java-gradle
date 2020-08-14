package com.vitech.moodfeed;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebMediumTest extends MediumTest {

    @LocalServerPort
    private int port;

    private static final TestRestTemplate REST_TEMPLATE = new TestRestTemplate();

    protected String baseUrl() {
        return "http://localhost:" + port;
    }

    protected TestRestTemplate restTemplate() {
        return REST_TEMPLATE;
    }

}
