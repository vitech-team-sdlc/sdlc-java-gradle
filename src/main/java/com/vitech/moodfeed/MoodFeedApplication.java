package com.vitech.moodfeed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

@SpringBootApplication
public class MoodFeedApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoodFeedApplication.class, args);
    }

    void YYYY(){
        String str = "";
        Pattern.compile("([");
        str.matches("([");
        str.replaceAll("([", "{");

        str.matches("(\\w+-(\\d+)");
        str.matches("(?<name>\\w+)-\\k<nae>");

        AtomicInteger aInt1 = new AtomicInteger(0);
        AtomicInteger aInt2 = new AtomicInteger(0);

        if (aInt1.equals(aInt2)) {

        }
    }

}
