package com.vitech.moodfeed;

import com.vitech.moodfeed.helpers.KeycloakContainer;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebMediumTest extends MediumTest {

    private static final String CHANGE_LOG = "db/db.changelog-master.xml";

    private static final String KEYCLOAK_TEST_REALM = "mood-feed";
    private static final String KEYCLOAK_TEST_CLIENT = "mood-feed-local";
    private static final String KEYCLOAK_ACCOUNT_USER = "homer";
    private static final String KEYCLOAK_ACCOUNT_ADMIN = "lisa";
    private static final String KEYCLOAK_ACCOUNT_PASSWORD = "123qweasd";
    protected static final String KEYCLOAK_ROLE_USER = "USER";
    protected static final String KEYCLOAK_ROLE_ADMIN = "ADMIN";

    private static final KeycloakContainer KEYCLOAK_CONTAINER;

    static {
        KEYCLOAK_CONTAINER = new KeycloakContainer().withRealmImportFile("/keycloak-realm-mood-feed.json");
        KEYCLOAK_CONTAINER.start();
    }

    @Autowired
    private TestRestTemplate restTemplate;

    private Liquibase liquibase;

    @DynamicPropertySource
    static void keycloakProperties(DynamicPropertyRegistry registry) {
        registry.add(
                "spring.security.oauth2.resourceserver.jwt.jwk-set-uri",
                () -> KEYCLOAK_CONTAINER.getAuthServerUrl(KEYCLOAK_TEST_REALM)
        );
    }

    @AfterEach
    public void afterEach(@Autowired DataSource dataSource) throws SQLException, LiquibaseException {
        initLiquibase(dataSource.getConnection());
        liquibase.dropAll();
        liquibase.update((String) null);
    }

    private void initLiquibase(Connection connection) throws LiquibaseException {
        if (Objects.isNull(liquibase)) {
            liquibase = new Liquibase(CHANGE_LOG, new ClassLoaderResourceAccessor(), new JdbcConnection(connection));
        }
    }

    protected TestRestTemplate restTemplate() {
        return restTemplate(KEYCLOAK_ROLE_USER);
    }

    protected TestRestTemplate restTemplate(String userRole) {
        final String token = getAccountToken(userRole);
        restTemplate.getRestTemplate().getInterceptors().clear();
        restTemplate.getRestTemplate().setInterceptors(Lists.newArrayList((request, body, execution) -> {
            request.getHeaders().set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            return execution.execute(request, body);
        }));
        return restTemplate;
    }

    private String getAccountToken(String userRole) {
        Keycloak keycloak = Keycloak.getInstance(
                KEYCLOAK_CONTAINER.getServerUrl(),
                KEYCLOAK_TEST_REALM,
                KEYCLOAK_ROLE_ADMIN.equals(userRole) ? KEYCLOAK_ACCOUNT_ADMIN : KEYCLOAK_ACCOUNT_USER,
                KEYCLOAK_ACCOUNT_PASSWORD,
                KEYCLOAK_TEST_CLIENT
        );
        return keycloak.tokenManager().getAccessTokenString();
    }

}
