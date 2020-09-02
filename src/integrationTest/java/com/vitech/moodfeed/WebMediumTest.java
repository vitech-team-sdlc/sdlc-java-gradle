package com.vitech.moodfeed;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebMediumTest extends MediumTest {

    private static final String CHANGE_LOG = "db/db.changelog-master.xml";

    @Autowired
    private TestRestTemplate restTemplate;

    private Liquibase liquibase;

    @AfterEach
    public void beforeEach(@Autowired DataSource dataSource) throws Exception {
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
        return restTemplate;
    }

}
