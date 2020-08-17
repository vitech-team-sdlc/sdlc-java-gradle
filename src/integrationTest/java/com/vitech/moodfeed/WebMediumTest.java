package com.vitech.moodfeed;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebMediumTest extends MediumTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private Liquibase liquibase;

    @BeforeEach
    public void beforeEach(
            @Autowired DataSource dataSource,
            @Value("${spring.liquibase.change-log}") String sqlChangeLog) throws Exception {
        initLiquibase(dataSource.getConnection(), sqlChangeLog);
        liquibase.dropAll();
        liquibase.update((String) null);
    }

    private void initLiquibase(Connection connection, String sqlChangeLog) throws LiquibaseException {
        if (Objects.isNull(liquibase)) {
            liquibase = new Liquibase(sqlChangeLog, new ClassLoaderResourceAccessor(), new JdbcConnection(connection));
        }
    }

    protected TestRestTemplate restTemplate() {
        return restTemplate;
    }

}
