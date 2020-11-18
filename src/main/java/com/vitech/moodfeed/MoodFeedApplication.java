package com.vitech.moodfeed;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
@Log4j2
public class MoodFeedApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MoodFeedApplication.class, args);
    }

    @Autowired
    private Environment env;

    @Override
    public void run(String... args) throws Exception {
        log.info(
                "Security config: {} {} {}",
                env.getProperty("security.provider"),
                env.getProperty("security.providerRealm"),
                env.getProperty("security.providerUrl")
        );
    }
}
